package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(nav: NavController, vm: PouViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        StatusBars(vm.estado.value)

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(vm.spriteAtual()),
            contentDescription = "Pou",
            modifier = Modifier.size(220.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        MenuButton(R.drawable.icon_cozinha, "Cozinha") { nav.navigate("cozinha") }
        MenuButton(R.drawable.icon_banho, "Casa de Banho") { nav.navigate("banho") }
        MenuButton(R.drawable.icon_quarto, "Quarto") { nav.navigate("quarto") }
        MenuButton(R.drawable.icon_closet, "Closet") { nav.navigate("closet") }
        MenuButton(R.drawable.icon_shop, "Shop") { nav.navigate("shop") }
    }
}

@Composable
fun MenuButton(icon: Int, texto: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.padding(4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(icon),
                contentDescription = texto,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(texto)
        }
    }
}
