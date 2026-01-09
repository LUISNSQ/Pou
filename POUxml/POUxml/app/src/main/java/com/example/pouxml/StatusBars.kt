package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun StatusBars(estado: PouEstado) {
    Column {
        StatusRow(R.drawable.icon_fome, "Fome: ${estado.fome}")
        StatusRow(R.drawable.icon_energia, "Energia: ${estado.energia}")
        StatusRow(R.drawable.icon_felicidade, "Felicidade: ${estado.felicidade}")
        StatusRow(R.drawable.icon_higiene, "Higiene: ${estado.higiene}")
        StatusRow(R.drawable.icon_moedas, "Moedas: ${estado.moedas}")
    }
}

@Composable
fun StatusRow(icon: Int, texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(2.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = texto,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(texto)
    }
}
