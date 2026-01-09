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
fun BanhoScreen(nav: NavController, vm: PouViewModel = viewModel()) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Casa de Banho") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Button(onClick = { vm.tomarBanho() }) {
                Text("Lavar o Pou")
            }
            Button(onClick = { nav.popBackStack() }) {
                Text("Voltar")
            }
        }
    }
}
