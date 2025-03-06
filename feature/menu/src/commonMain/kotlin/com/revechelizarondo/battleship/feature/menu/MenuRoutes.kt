package com.revechelizarondo.battleship.feature.menu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MenuRoute

fun NavGraphBuilder.menuRoutes(
    goToConfig: () -> Unit,
    goToLeaderboard: () -> Unit,
    goToSettings: () -> Unit,
) {
    composable<MenuRoute> {
        MenuScreen(
            goToConfig = goToConfig,
            goToLeaderboard = goToLeaderboard,
            goToSettings = goToSettings,
            modifier = Modifier.fillMaxSize()
        )
    }
}
