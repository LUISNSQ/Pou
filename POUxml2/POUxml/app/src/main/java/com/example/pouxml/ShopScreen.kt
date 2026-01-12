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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ShopScreen(nav: NavController, vm: PouViewModel) {
    val estado = vm.estado.value

    //Lista dos objetos, depois podemos meter mais
    val itensLoja = listOf(
        ItemShop(1, "Maçã", 10, TipoItem.COMIDA, R.drawable.item_comida),
        ItemShop(2, "Hambúrguer", 25, TipoItem.COMIDA, R.drawable.item_comida),
        ItemShop(3, "Tree", 50, TipoItem.ROUPA, R.drawable.camisola1),
        ItemShop(4, "Alternative", 50, TipoItem.ROUPA, R.drawable.camisola2),
        ItemShop(5, "Bird", 50, TipoItem.ROUPA, R.drawable.camisola3),
        ItemShop(6, "Car", 50, TipoItem.ROUPA, R.drawable.camisola4),
        ItemShop(7, "Acessório 1", 100, TipoItem.ACESSORIO, R.drawable.acessorio1),
        ItemShop(8, "Acessório 2", 150, TipoItem.ACESSORIO, R.drawable.acessorio2),
        ItemShop(9, "Acessório 3", 150, TipoItem.ACESSORIO, R.drawable.acessorio3)
    )
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF0F0F0))) {
        // Moedas no topo e botao de voltar
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(onClick = { nav.popBackStack() }) { Text("Voltar") }
            Text("LOJA", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${estado.moedas}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.icon_moedas),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

            }

        }
        // Desenho das prateleiras
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {  // Para cada item da loja, percorre todos
            items(itensLoja) { item ->
                val jaAdquirido =
                    (item.tipo == TipoItem.ROUPA || item.tipo == TipoItem.ACESSORIO) &&
                            estado.inventario.any { it.id == item.id }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clickable {
                            vm.adInventario(item)
                        },
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) { //Imagem do item
                    Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                        Text(item.nome, fontWeight = FontWeight.Bold)
                        Image(
                            painter = painterResource(item.imagemRes),
                            contentDescription = item.nome,
                            modifier = Modifier.size(60.dp).align(Alignment.Center)
                        )

                        // Preço de cada item em cada prateleira no canto inferior direito
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .background(
                                    if (jaAdquirido) Color.Gray else Color(0xFFFFD700),
                                    shape = MaterialTheme.shapes.small
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (jaAdquirido) { // Se ja foi comprado tira o preço
                                Text(
                                    text = "ADQUIRIDO",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            } else {
                                Text(
                                    text = "${item.preco}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.icon_moedas),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                        }

                    }

                }

            }


        }
    }
}

