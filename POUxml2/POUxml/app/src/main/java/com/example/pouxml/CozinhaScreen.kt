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

        MainLayout(nav = nav, vm = vm, titulo = "COZINHA", esquerda = "home", direita = "banho", customBottomLeftIcon = R.drawable.icon_fridge, onBottomLeftClick = { nav.navigate("fridge") }) {

            if (estado.stockComida.isNotEmpty()) {
                val primeiraComida = estado.stockComida.first()

                Box(modifier = Modifier.offset {
                    IntOffset(
                        comidaOffset.x.roundToInt(),
                        comidaOffset.y.roundToInt()
                    )
                }
                    .align(Alignment.BottomCenter).padding(bottom = 140.dp).size(110.dp) // Ajustado para 110dp para equilíbrio visual
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                if (comidaOffset.y < -150f) {
                                    vm.comer(primeiraComida)
                                    comidaOffset = Offset.Zero
                                } else {
                                    comidaOffset = Offset.Zero
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
                Text(
                    "Sem comida!",
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 140.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
