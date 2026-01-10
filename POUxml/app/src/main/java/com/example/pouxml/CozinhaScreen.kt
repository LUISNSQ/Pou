package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.roundToInt

@Composable
fun CozinhaScreen(nav: NavController, vm: PouViewModel) {
    val estado = vm.estado.value
    var comidaOffset by remember { mutableStateOf(Offset.Zero) }
    var pouPosition by remember { mutableStateOf(Offset.Zero) }
    
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Topo: Status e Navegação
        Column(modifier = Modifier.fillMaxWidth()) {
            StatusBars(estado)
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { nav.navigate("quarto") }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quarto")
                }
                Text("COZINHA", style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = { nav.navigate("banho") }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Banho")
                }
            }
        }

        // 2. Centro: Pou (com deteção de posição)
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(vm.spriteAtual()),
                contentDescription = "Pou",
                modifier = Modifier
                    .size(240.dp)
                    .onGloballyPositioned { coordinates ->
                        pouPosition = coordinates.positionInRoot()
                    }
            )
        }

        // 3. Comida Arrastável (Stock)
        if (estado.stockComida.isNotEmpty()) {
            val primeiraComida = estado.stockComida.first()
            
            Box(
                modifier = Modifier
                    .offset { IntOffset(comidaOffset.x.roundToInt(), comidaOffset.y.roundToInt()) }
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 120.dp) // Posicionada acima da barra de ícones
                    .size(80.dp)
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
                                    comidaOffset = Offset.Zero // Volta para o sítio se não chegou ao Pou
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

        // 4. Barra Inferior (Sem botão alimentar, apenas ícones)
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

            // Espaço vazio onde estava o botão
            Spacer(modifier = Modifier.width(160.dp))

            IconButton(onClick = { nav.navigate("shop") }) {
                Image(painter = painterResource(R.drawable.icon_shop), contentDescription = "Shop", modifier = Modifier.size(60.dp))
            }
        }
    }
}
