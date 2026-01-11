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
fun QuartoScreen(nav: NavController, vm: PouViewModel) {
    val estado = vm.estado.value

    // Desenha o fundo
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.cenario4),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        MainLayout(nav = nav, vm = vm, titulo = "QUARTO", esquerda = "banho", direita = "home") {
            // Teste
            if (estado.sleeping) {
                Text(
                    text = "Zzzzz...",
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 150.dp),
                    fontSize = 40.sp,
                    color = Color.Gray
                )
            }
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
                    .clickable { vm.dormir() }) {
                Image(
                    painter = painterResource(R.drawable.item_cama),
                    contentDescription = "Cama",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}