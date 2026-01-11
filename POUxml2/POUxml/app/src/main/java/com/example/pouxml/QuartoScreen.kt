package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun QuartoScreen(nav: NavController, vm: PouViewModel) {
    val estaDormindo = vm.estado.value.estaDormindo
    
    Box(modifier = Modifier.fillMaxSize()) {
        MainLayout(nav = nav, vm = vm, titulo = "QUARTO", esquerda = "banho", direita = "cozinha") {
            
            // 1. Sobreposição escura se estiver a dormir (por cima de tudo)
            if (estaDormindo) {
                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.6f)))
            }

            // 2. Item da Cama (Em baixo no meio)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 120.dp)
                    .size(150.dp)
                    .clickable { 
                        vm.alternarSono()
                        if (!estaDormindo) {
                            vm.dormir() 
                        }
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.item_cama),
                    contentDescription = "Cama",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
