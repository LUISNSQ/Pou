package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun QuartoScreen(nav: NavController, vm: PouViewModel = viewModel()) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.item_cama),
            contentDescription = "Dormir",
            modifier = Modifier.size(140.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { vm.dormir() }) {
            Text("Dormir")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { nav.popBackStack() }) {
            Text("Voltar")
        }
    }
}
