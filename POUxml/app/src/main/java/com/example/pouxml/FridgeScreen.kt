package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FridgeScreen(nav: NavController, vm: PouViewModel) {
    val estado = vm.estado.value
    
    //Agora agrupamos a comida por ID para separar Maçãs de hambúrgueres, etc
    val stockAgrupado = estado.stockComida.groupBy { it }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFADD8E6))) { 
        Column(modifier = Modifier.fillMaxSize()) {
            // Topo (Igual ao Closet)
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { nav.popBackStack() }) { Text("Voltar") }
                Text("FRIGORÍFICO", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Box(modifier = Modifier.size(60.dp))
            }

            // Desenho do Xamuel central (igual ao closet)
            Box(modifier = Modifier.fillMaxWidth().height(260.dp), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(vm.spriteAtual()), contentDescription = "Pou", modifier = Modifier.size(200.dp))
                estado.roupaEquipada?.let {
                    Image(painter = painterResource(it.imagemRes), contentDescription = null, modifier = Modifier.size(200.dp))
                }
                estado.acessorioEquipado?.let {
                    Image(painter = painterResource(it.imagemRes), contentDescription = null, modifier = Modifier.size(200.dp).offset(y = (-55).dp))
                }
            }

            // Itens Separados
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item(span = { GridItemSpan(3) }) {
                    Text("STOCK DE COMIDA", color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
                }

                if (estado.stockComida.isEmpty()) {
                    item(span = { GridItemSpan(3) }) {
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text("O frigorífico está vazio!", fontSize = 18.sp, color = Color.Gray)
                        }
                    }
                } else {
                    // Para cada tipo de comida diferente no stock, criamos um card
                    items(stockAgrupado.keys.toList()) { itemRes ->
                        val quantidade = stockAgrupado[itemRes]?.size ?: 0
                        
                        // Determinar o nome baseado no recurso
                        val nomeComida = when(itemRes) {
                            R.drawable.item_maca -> "Maçã"
                            R.drawable.item_hamburguer -> "Hambúrguer"
                            else -> "Comida"
                        }

                        FridgeItemCard(itemRes, nomeComida, quantidade) {
                            vm.comer(itemRes)
                        }
                    }
                }

                item(span = { GridItemSpan(3) }) {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun FridgeItemCard(itemRes: Int, nome: String, quantidade: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier.size(100.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(itemRes), 
                    contentDescription = nome, 
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    nome, 
                    fontSize = 10.sp, 
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    "x$quantidade", 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.DarkGray
                )
            }
        }
    }
}
