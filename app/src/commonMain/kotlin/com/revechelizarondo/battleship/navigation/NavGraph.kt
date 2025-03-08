package com.revechelizarondo.battleship.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.revechelizarondo.battleship.feature.config.ConfigGameSettingsRoute
import com.revechelizarondo.battleship.feature.config.configRoute
import com.revechelizarondo.battleship.feature.menu.MenuRoute
import com.revechelizarondo.battleship.feature.menu.menuRoutes
import com.revechelizarondo.battleship.feature.settings.SettingsRoute
import com.revechelizarondo.battleship.feature.settings.settingsRoute

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MenuRoute
    ) {
        menuRoutes(
            goToConfig = {
                navController.navigate(ConfigGameSettingsRoute)
            },
            goToLeaderboard = { },
            goToSettings = {
                navController.navigate(SettingsRoute)
            }
        )
        settingsRoute {
            navController.navigateUp()
        }
        configRoute(
            navigateUp = {
                navController.navigateUp()
            },
            goToGame = {

            }
        )
    }
}