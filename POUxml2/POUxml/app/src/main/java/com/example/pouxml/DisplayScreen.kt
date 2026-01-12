package com.example.pouxml

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DisplayScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    var listaJogadores by remember { mutableStateOf(listOf<PouEstado>()) }

    // Carregar dados do Firebase
    LaunchedEffect(Unit) {
        db.collection("jogadores").get()
            .addOnSuccessListener { resultado ->
                val lista = resultado.mapNotNull { doc ->
                    try {
                        doc.toObject(PouEstado::class.java)
                    } catch (e: Exception) {
                        Log.e("Firebase", "Erro ${doc.id}: ${e.message}")
                        null
                    }
                }
                listaJogadores = lista
            }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Amiguinhos", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(listaJogadores) { jogador ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //desenha o amiguinho
                        Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
                            Image(painterResource(id = R.drawable.pou_idle), contentDescription = null)

                            // se tiver roupa, desenha
                            jogador.roupaEquipada?.let { roupa ->
                                if (roupa.imagemRes != 0) {
                                    Image(painterResource(id = roupa.imagemRes), contentDescription = null)
                                }
                            }

                            // se tiver acessorio tambem o desenha
                            jogador.acessorioEquipado?.let { acessorio ->
                                if (acessorio.imagemRes != 0) {
                                    Image(
                                        painter = painterResource(id = acessorio.imagemRes),
                                        contentDescription = null,
                                        modifier = Modifier.offset(y = (-15).dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // nome do user
                        Column(verticalArrangement = Arrangement.Center) {
                            Text(
                                text = jogador.nomeUser.ifEmpty { "Solid Snake" },
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("VOLTAR")
        }
    }
}
