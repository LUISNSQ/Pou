package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.unit.sp

@Composable
fun ShopScreen(nav: NavController, vm: PouViewModel) {
    val estado = vm.estado.value

    val itensLoja = listOf(
        ItemShop(1, "Maçã", 10, TipoItem.COMIDA, R.drawable.item_comida),
        ItemShop(2, "Hambúrguer", 25, TipoItem.COMIDA, R.drawable.item_comida),
        ItemShop(3, "Alternative", 50, TipoItem.ROUPA, R.drawable.camisola1),
        ItemShop(4, "Tree", 50, TipoItem.ROUPA, R.drawable.camisola2),
        ItemShop(5, "Bird", 50, TipoItem.ROUPA, R.drawable.camisola3),
        ItemShop(6, "Car", 50, TipoItem.ROUPA, R.drawable.camisola4),
        ItemShop(7, "Acessório 1", 100, TipoItem.ACESSORIO, R.drawable.acessorio1),
        ItemShop(8, "Acessório 2", 150, TipoItem.ACESSORIO, R.drawable.acessorio2)
    )

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF0F0F0))) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { nav.popBackStack() }) { Text("Voltar") }
            Text("LOJA", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${estado.moedas}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.width(4.dp))
                Image(painter = painterResource(id = R.drawable.icon_moedas),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(itensLoja) { item ->
                val jaAdquirido = (item.tipo == TipoItem.ROUPA || item.tipo == TipoItem.ACESSORIO) && 
                                  estado.inventario.any { it.id == item.id }

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clickable(enabled = !jaAdquirido) { vm.comprarItem(item) },
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (jaAdquirido) Color.LightGray else Color.White
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                        Text(item.nome, fontWeight = FontWeight.Bold)
                        Image(painter = painterResource(item.imagemRes),
                            contentDescription = item.nome,
                            modifier = Modifier.size(60.dp).align(Alignment.Center))
                        
                        Row(modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .background(if (jaAdquirido) Color.Gray else Color(0xFFFFD700), shape = MaterialTheme.shapes.small)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            if (jaAdquirido) {
                                Text(text = "ADQUIRIDO", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                            } else {
                                Text(text = "${item.preco}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(painter = painterResource(id = R.drawable.icon_moedas),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
