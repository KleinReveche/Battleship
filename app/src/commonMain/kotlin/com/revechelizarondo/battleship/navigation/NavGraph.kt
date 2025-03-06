package com.revechelizarondo.battleship.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.revechelizarondo.battleship.feature.menu.MenuRoute
import com.revechelizarondo.battleship.feature.menu.menuRoutes

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MenuRoute
    ) {
        menuRoutes(
            goToConfig = { },
            goToLeaderboard = { },
            goToSettings = { }
        )
    }
}