package com.revechelizarondo.battleship.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.revechelizarondo.battleship.features.main.MainScreen
import com.revechelizarondo.battleship.features.main.MainScreenDestination
import com.revechelizarondo.battleship.features.settings.Settings

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreenDestination
    ) {
        composable<MainScreenDestination> {
            MainScreen(navController)
        }
        composable("settings") {
            Settings(navController)
        }
    }
}
