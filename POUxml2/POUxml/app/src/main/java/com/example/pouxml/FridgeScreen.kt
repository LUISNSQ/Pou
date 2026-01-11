package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
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
    
    // Agrupa o stock de comida por ID do item para contar as quantidades
    val stockAgrupado = estado.stockComida.groupBy { it.id }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFADD8E6))) { // Cor de Cozinha
        Column(modifier = Modifier.fillMaxSize()) {
            // Topo
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { nav.popBackStack() }) { Text("Voltar") }
                Text("FRIGORÍFICO", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Box(modifier = Modifier.size(60.dp))
            }

            Text(
                "STOCK DE COMIDA",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 colunas para caber o nome melhor
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(stockAgrupado.values.toList()) { listaItens ->
                    val itemExemplo = listaItens.first()
                    val quantidade = listaItens.size
                    FoodStockCard(itemExemplo, quantidade)
                }
            }
        }
    }
}

@Composable
fun FoodStockCard(item: ItemShop, quantidade: Int) {
    Card(
        modifier = Modifier.fillMaxWidth().height(140.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.nome,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    painter = painterResource(item.imagemRes),
                    contentDescription = item.nome,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stock: $quantidade",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = Color(0xFF2E7D32) // Verde escuro
                )
            }
        }
    }
}
