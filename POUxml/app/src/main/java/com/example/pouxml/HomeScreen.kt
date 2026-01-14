package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun HomeScreen(nav: NavController, vm: PouViewModel) {
    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(Offset.Zero) }
    
    // Reduzido para 110dp para equilibrar visualmente com a cama/sabão
    val ballSize = 110.dp 
    val ballSizePx = with(density) { ballSize.toPx() }

    var ballPosition by remember { mutableStateOf(Offset.Zero) }
    var ballVelocity by remember { mutableStateOf(Offset.Zero) }
    var isDragging by remember { mutableStateOf(false) }
    var initialPositionSet by remember { mutableStateOf(false) }

    LaunchedEffect(isDragging, containerSize) {
        if (!isDragging && containerSize != Offset.Zero) {
            while (true) {
                if (ballVelocity.getDistance() > 0.5f) {
                    val vX = ballVelocity.x
                    val vY = ballVelocity.y
                    val nextX = ballPosition.x + vX
                    val nextY = ballPosition.y + vY
                    var newVX = vX
                    var newVY = vY

                    if (nextX <= 0f || nextX >= containerSize.x - ballSizePx) {
                        newVX *= -0.8f
                        if (ballVelocity.getDistance() > 2f) vm.pouFeliz()
                    }
                    if (nextY <= 0f || nextY >= containerSize.y - ballSizePx) {
                        newVY *= -0.8f
                        if (ballVelocity.getDistance() > 2f) vm.pouFeliz()
                    }

                    ballPosition = Offset(
                        (ballPosition.x + newVX).coerceIn(0f, containerSize.x - ballSizePx),
                        (ballPosition.y + newVY).coerceIn(0f, containerSize.y - ballSizePx)
                    )
                    ballVelocity = Offset(newVX * 0.97f, newVY * 0.97f)
                } else {
                    ballVelocity = Offset.Zero
                }
                delay(16)
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned { layoutCoordinates ->
            containerSize = Offset(layoutCoordinates.size.width.toFloat(), layoutCoordinates.size.height.toFloat())
            if (!initialPositionSet) {
                ballPosition = Offset(
                    (containerSize.x / 2) - (ballSizePx / 2),
                    containerSize.y - ballSizePx - with(density) { 140.dp.toPx() }
                )
                initialPositionSet = true
            }
        }
    ) {
        // Aplicado o novo cenário
        Image(
            painter = painterResource(id = R.drawable.cenario1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        MainLayout(nav = nav, vm = vm, titulo = "SALA", esquerda = "quarto", direita = "cozinha") {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .offset { IntOffset(ballPosition.x.roundToInt(), ballPosition.y.roundToInt()) }
                        .size(ballSize)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { 
                                    isDragging = true
                                    ballVelocity = Offset.Zero 
                                },
                                onDragEnd = { isDragging = false },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    ballPosition += dragAmount
                                    ballVelocity = dragAmount * 1.8f 
                                    vm.pouFelizGradual() 
                                }
                            )
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.item_bola),
                        contentDescription = "Bola",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
