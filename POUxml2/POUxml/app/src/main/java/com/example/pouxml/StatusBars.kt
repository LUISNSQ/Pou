package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StatusBars(estado: PouEstado, nav: NavController) {
    // Barra dos stats
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp) 
            .background(Color.Black.copy(alpha = 0.12f))
            .padding(vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícones  aumentados para 52dp
            StatusRow(R.drawable.icon_fome, estado.fome, Color(0xFFFF4444))
            StatusRow(R.drawable.icon_energia, estado.energia, Color(0xFFFFD700))
            StatusRow(R.drawable.icon_felicidade, estado.felicidade, Color(0xFF4CAF50))
            StatusRow(R.drawable.icon_higiene, estado.higiene, Color(0xFF03A9F4))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.25f), shape = RoundedCornerShape(14.dp))
                    .clickable { nav.navigate("display") }
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.amiguinhos),
                    contentDescription = "moedas",
                    modifier = Modifier.size(52.dp) 
                )
            }
        }
    }
}

@Composable
fun StatusRow(icon: Int, valor: Int, corBarra: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(52.dp)
        )
        
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(18.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.75f))
                .border(2.dp, Color.Black.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = (valor.coerceIn(0, 100) / 100f))
                    .background(corBarra)
            )
        }
    }
}
