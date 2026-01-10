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
fun BanhoScreen(nav: NavController, vm: PouViewModel) {
    var sabaoOffset by remember { mutableStateOf(Offset.Zero) }
    val estado = vm.estado.value

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.cenario3),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds)

        MainLayout(nav = nav, vm = vm, titulo = "BANHO", esquerda = "cozinha", direita = "quarto") {
            // É o Xamuel UwU
            Image(
                painter = painterResource(vm.spriteAtual()),
                contentDescription = "Pou",
                modifier = Modifier.size(240.dp)
            )

            Box(
                modifier = Modifier
                .offset { IntOffset(sabaoOffset.x.roundToInt(), sabaoOffset.y.roundToInt()) }
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp)
                .size(80.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            sabaoOffset = Offset.Zero
                        },
                        onDrag = { toque, drag ->
                            toque.consume()
                            sabaoOffset += drag

                            if (sabaoOffset.y < -150f) {
                                vm.tomarBanho()
                            }
                        }
                    )
                }
            ) {
                Image(
                    painter = painterResource(R.drawable.item_banho),
                    contentDescription = "Sabonete",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }


    }
}
