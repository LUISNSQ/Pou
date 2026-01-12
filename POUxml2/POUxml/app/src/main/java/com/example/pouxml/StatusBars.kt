package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StatusBars(estado: PouEstado, nav: NavController) {
    // Troquei de Column para Row para ficar mais Bunito
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatusRow(R.drawable.icon_fome, estado.fome)
            StatusRow(R.drawable.icon_energia, estado.energia)
            StatusRow(R.drawable.icon_felicidade, estado.felicidade)
            StatusRow(R.drawable.icon_higiene, estado.higiene)
            //Botao para ver os amiguinhos <3, mudar por causa das moedas
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { nav.navigate("display") }
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_moedas),
                    contentDescription = "gamers",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = estado.moedas.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1
                )
        }}

    }
}

@Composable
fun StatusRow(icon: Int, valor: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(2.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(32.dp))

        Spacer(modifier = Modifier.width(6.dp))
        Text( text = valor.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold, // Texto mais visível
            maxLines = 1)
    }
}
