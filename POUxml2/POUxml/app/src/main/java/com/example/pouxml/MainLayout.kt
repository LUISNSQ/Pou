package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainLayout(
    nav: NavController,
    vm: PouViewModel,
    titulo: String,
    esquerda: String,
    direita: String,
    customBottomLeftIcon: Int? = null,
    onBottomLeftClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val estado = vm.estado.value

    // Define a cor de fundo com base no título (ecrã)
    val backgroundColor = when (titulo) {
        "SALA" -> Color(0xFFD2B48C)       // Tan / Beje castanho
        "COZINHA" -> Color(0xFFADD8E6)    // Light Blue / Azul claro
        "BANHO" -> Color(0xFFFFFFFF)      // Branco
        "QUARTO" -> Color(0xFF2C3E50)     // Azul Escuro (Midnight Blue)
        else -> Color.White
    }

    // Texto dinâmico para os ícones (branco no quarto para contrastar)
    val contentColor = if (titulo == "QUARTO") Color.White else Color.Black

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            StatusBars(estado) // Status

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { nav.navigate(esquerda) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = contentColor)
                }
                Text(titulo, style = MaterialTheme.typography.titleLarge, color = contentColor)
                IconButton(onClick = { nav.navigate(direita) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = contentColor)
                }
            }
        }

        // Conteúdo central (Pou e botões de ação)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Desenha o Pou
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(contentAlignment = Alignment.Center) {
                    // 1. Corpo do Pou
                    Image(
                        painter = painterResource(vm.spriteAtual()),
                        contentDescription = "Pou",
                        modifier = Modifier.size(240.dp)
                    )
                    // 2. Roupa (Camisola)
                    estado.roupaEquipada?.let { roupa ->
                        Image(
                            painter = painterResource(roupa.imagemRes),
                            contentDescription = "Roupa Equipada",
                            modifier = Modifier.size(240.dp)
                        )
                    }
                    // 3. Acessório (Cabeça)
                    estado.acessorioEquipado?.let { acessorio ->
                        Image(
                            painter = painterResource(acessorio.imagemRes),
                            contentDescription = "Acessório Equipado",
                            modifier = Modifier
                                .size(240.dp)
                                .offset(y = (-100).dp)
                        )
                    }
                }
            }
            
            content()
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botão Inferior Esquerdo (Customizável)
            val leftIcon = customBottomLeftIcon ?: R.drawable.icon_closet
            val leftAction = onBottomLeftClick ?: { nav.navigate("closet") }

            IconButton(onClick = leftAction) {
                Image(
                    painter = painterResource(leftIcon),
                    contentDescription = "Bottom Left Action", 
                    modifier = Modifier.size(60.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(100.dp))

            IconButton(onClick = { nav.navigate("shop") }) {
                Image(
                    painter = painterResource(R.drawable.icon_shop),
                    contentDescription = "Shop", modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}
