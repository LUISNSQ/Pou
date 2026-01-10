package com.example.pouxml

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val vm: PouViewModel = viewModel()

    NavHost(navController, startDestination = "home") { // Começa direto na sala
        composable("home") { HomeScreen(navController, vm) }
        composable("cozinha") { CozinhaScreen(navController, vm) }
        composable("banho") { BanhoScreen(navController, vm) }
        composable("quarto") { QuartoScreen(navController, vm) }
        composable("closet") { ClosetScreen(navController) }
        composable("shop") { ShopScreen(navController) }
    }
}
