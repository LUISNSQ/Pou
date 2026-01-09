package com.example.pouxml

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("cozinha") { CozinhaScreen(navController) }
        composable("banho") { BanhoScreen(navController) }
        composable("quarto") { QuartoScreen(navController) }
        composable("closet") { ClosetScreen(navController) }
        composable("shop") { ShopScreen(navController) }
    }
}
