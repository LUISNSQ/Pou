package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.roundToInt

@Composable
fun CozinhaScreen(nav: NavController, vm: PouViewModel) {
    var comidaOffset by remember { mutableStateOf(Offset.Zero) }
    val estado = vm.estado.value
    // Desenha o fundo
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.cenario2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        MainLayout(nav = nav, vm = vm, titulo = "COZINHA", esquerda = "home", direita = "banho") {
            // É o Xamuel UwU
            Image(
                painter = painterResource(vm.spriteAtual()),
                contentDescription = "Pou",
                modifier = Modifier.size(240.dp)
            )

            if (estado.stockComida.isNotEmpty()) {
                val primeiraComida = estado.stockComida.first()

                Box(modifier = Modifier.offset {
                    IntOffset(
                        comidaOffset.x.roundToInt(),
                        comidaOffset.y.roundToInt()
                    )
                }
                    .align(Alignment.BottomCenter).padding(bottom = 120.dp).size(80.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                // Verifica colisão simples: se a comida está perto do centro do Pou
                                // pouPosition é o canto superior esquerdo do Pou.
                                // O centro do Pou está aprox em pouPosition + 120dp
                                if (comidaOffset.y < -150f) { // Se arrastou para cima o suficiente
                                    vm.alimentar()
                                    // Reset da posição para a próxima comida (ou lógica de remover do stock)
                                    comidaOffset = Offset.Zero
                                } else {
                                    comidaOffset =
                                        Offset.Zero // Volta para o sítio se não chegou ao Pou
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                comidaOffset += dragAmount
                            }
                        )
                    }
                ) {
                    Image(
                        painter = painterResource(primeiraComida),
                        contentDescription = "Comida para arrastar",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                // Se não houver comida
                Text(
                    "Sem comida!",
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 140.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }


        }

    }
}