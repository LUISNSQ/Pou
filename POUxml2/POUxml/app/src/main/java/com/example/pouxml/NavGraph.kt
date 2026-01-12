package com.example.pouxml

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val vm: PouViewModel = viewModel()
    val loginVm: LoginViewModel = viewModel()

    NavHost(navController, startDestination = "login") { // substituir para login
        composable("login") { LoginScreenView(navController, loginVm, vm) }
        composable("home") { HomeScreen(navController, vm) }
        composable("cozinha") { CozinhaScreen(navController, vm) }
        composable("banho") { BanhoScreen(navController, vm) }
        composable("quarto") { QuartoScreen(navController, vm) }
        composable("closet") { ClosetScreen(navController, vm) }
        composable("shop") { ShopScreen(navController, vm) }
        composable("display") { DisplayScreen(navController)}
    }
}
