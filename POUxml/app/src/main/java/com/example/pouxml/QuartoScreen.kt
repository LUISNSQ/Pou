package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun QuartoScreen(nav: NavController, vm: PouViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Topo: Status e Navegação
        Column(modifier = Modifier.fillMaxWidth()) {
            StatusBars(vm.estado.value)
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { nav.navigate("banho") }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Banho")
                }
                Text("QUARTO", style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = { nav.navigate("cozinha") }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Cozinha")
                }
            }
        }

        // 2. Centro: Pou e Item
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.item_cama),
                contentDescription = "Cama",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(vm.spriteAtual()),
                contentDescription = "Pou",
                modifier = Modifier.size(240.dp)
            )
        }

        // 3. Barra Inferior: Closet | Ação | Shop
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { nav.navigate("closet") }) {
                Image(painter = painterResource(R.drawable.icon_closet), contentDescription = "Closet", modifier = Modifier.size(60.dp))
            }

            Button(
                onClick = { vm.dormir() },
                modifier = Modifier.height(64.dp).width(160.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("DORMIR", style = MaterialTheme.typography.titleMedium)
            }

            IconButton(onClick = { nav.navigate("shop") }) {
                Image(painter = painterResource(R.drawable.icon_shop), contentDescription = "Shop", modifier = Modifier.size(60.dp))
            }
        }
    }
}
