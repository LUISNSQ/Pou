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
fun ClosetScreen(nav: NavController, vm: PouViewModel) {
    val estado = vm.estado.value
    val roupasNoCloset = estado.inventario.filter { it.tipo == TipoItem.ROUPA }
    val acessoriosNoCloset = estado.inventario.filter { it.tipo == TipoItem.ACESSORIO }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF8B4513))) { // Fundo Castanho
        Column(modifier = Modifier.fillMaxSize()) {
            // Topo
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { nav.popBackStack() }) { Text("Voltar") }
                Text("CLOSET", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Box(modifier = Modifier.size(60.dp))
            }

            // Visualização do Pou
            Box(modifier = Modifier.fillMaxWidth().height(260.dp), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(vm.spriteAtual()), contentDescription = "Pou", modifier = Modifier.size(200.dp))
                estado.roupaEquipada?.let {
                    Image(painter = painterResource(it.imagemRes), contentDescription = null, modifier = Modifier.size(200.dp))
                }
                estado.acessorioEquipado?.let {
                    Image(painter = painterResource(it.imagemRes), contentDescription = null, modifier = Modifier.size(200.dp).offset(y = (-35).dp))
                }
            }

            // Lista de Itens
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Secção de ACESSÓRIOS primeiro
                item(span = { GridItemSpan(3) }) {
                    Text("ACESSÓRIOS", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
                }

                item {
                    ClosetItemCard(null, estado.acessorioEquipado == null, "Tirar") { vm.equiparAcessorio(null) }
                }
                items(acessoriosNoCloset) { acc ->
                    ClosetItemCard(acc, estado.acessorioEquipado?.id == acc.id) { vm.equiparAcessorio(acc) }
                }

                // Secção de ROUPAS em baixo
                item(span = { GridItemSpan(3) }) {
                    Text("ROUPAS", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                }

                item {
                    ClosetItemCard(null, estado.roupaEquipada == null, "Tirar") { vm.vestirRoupa(null) }
                }
                items(roupasNoCloset) { roupa ->
                    ClosetItemCard(roupa, estado.roupaEquipada?.id == roupa.id) { vm.vestirRoupa(roupa) }
                }
                
                // Espaçamento final
                item(span = { GridItemSpan(3) }) {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun ClosetItemCard(item: ItemShop?, estaEquipado: Boolean, placeholder: String = "", onClick: () -> Unit) {
    Card(
        modifier = Modifier.size(100.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = if (estaEquipado) Color(0xFFFFD700) else Color.White.copy(alpha = 0.8f))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (item == null) {
                Text(placeholder, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            } else {
                Image(painter = painterResource(item.imagemRes), contentDescription = item.nome, modifier = Modifier.size(70.dp))
            }
        }
    }
}
