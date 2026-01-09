package com.example.pouxml

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CozinhaScreen(nav: NavController, vm: PouViewModel = viewModel()) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Cozinha") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Button(onClick = { vm.alimentar() }) {
                Text("Dar comida ao Pou")
            }
            Button(onClick = { nav.popBackStack() }) {
                Text("Voltar")
            }
        }
    }
}
