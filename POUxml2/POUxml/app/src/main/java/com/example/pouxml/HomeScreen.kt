package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(nav: NavController, vm: PouViewModel) {
    val estado = vm.estado.value
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.cenario1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        MainLayout(nav = nav, vm = vm, titulo = "SALA", esquerda = "quarto", direita = "cozinha") {
            // É o Xamuel UwU
            Image(
                painter = painterResource(vm.spriteAtual()),
                contentDescription = "Pou",
                modifier = Modifier.size(240.dp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 120.dp)
                    .size(150.dp)
                    .clickable { vm.pouFeliz() }) {
                Image(
                    painter = painterResource(R.drawable.roupa_chapeu),
                    contentDescription = "Cama",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}