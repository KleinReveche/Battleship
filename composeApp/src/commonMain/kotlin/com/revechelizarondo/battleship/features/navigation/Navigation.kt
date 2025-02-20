package com.revechelizarondo.battleship.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.revechelizarondo.battleship.features.Menu.MenuScreen
import com.revechelizarondo.battleship.features.main.MainScreen
import com.revechelizarondo.battleship.features.settings.Settings

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(navController)
        }
        composable("menu") {
            MenuScreen(navController)
        }
        composable("settings") {
            Settings(navController)
        }
    }
}
