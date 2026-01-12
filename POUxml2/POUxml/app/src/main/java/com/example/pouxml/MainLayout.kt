package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainLayout(
    nav: NavController, 
    vm: PouViewModel, 
    titulo: String, 
    esquerda: String, 
    direita: String, 
    customBottomLeftIcon: Int? = null,
    onBottomLeftClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val estado = vm.estado.value

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            StatusBars(estado, nav) // Status

            // Setas para a mudanca de direcao
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { nav.navigate(esquerda) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
                Text(titulo, style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = { nav.navigate(direita) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }

        // Conteúdo central (Xamuel e botões dedicados a cada screen)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Desenha o Pou
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(contentAlignment = Alignment.Center) {
                    //Pou
                    Image(
                        painter = painterResource(vm.spriteAtual()),
                        contentDescription = "Pou",
                        modifier = Modifier.size(240.dp)
                    )
                    // roupa
                    estado.roupaEquipada?.let { roupa ->
                        Image(
                            painter = painterResource(roupa.imagemRes),
                            contentDescription = "Roupa Equipada",
                            modifier = Modifier.size(240.dp)
                        )
                    }
                    // Acessório
                    estado.acessorioEquipado?.let { acessorio ->
                        Image(
                            painter = painterResource(acessorio.imagemRes),
                            contentDescription = "Acessório Equipado",
                            modifier = Modifier
                                .size(240.dp)
                                .offset(y = (-65).dp)
                        )
                    }
                }
            }
            content()
        }


        // Desenhos dos botões embaixo do layout geral
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(130.dp)
                .background(Color.Black.copy(alpha = 0.12f))
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val leftIcon = customBottomLeftIcon ?: R.drawable.icon_closet
                val leftAction = onBottomLeftClick ?: { nav.navigate("closet") }


                IconButton(
                    onClick = leftAction,
                    modifier = Modifier.size(110.dp) 
                ) {
                    Image(
                        painter = painterResource(leftIcon),
                        contentDescription = "Bottom Left Action", 
                        modifier = Modifier.size(100.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(40.dp))

                IconButton(
                    onClick = { nav.navigate("shop") },
                    modifier = Modifier.size(110.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.icon_shop),
                        contentDescription = "Shop", 
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        }
    }
}
