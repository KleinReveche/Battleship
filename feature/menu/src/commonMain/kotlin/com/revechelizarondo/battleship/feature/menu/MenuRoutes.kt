package com.revechelizarondo.battleship.feature.menu

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
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
    composable<MenuRoute>(
        exitTransition = {
            slideOutVertically(
                animationSpec = tween(1000),
                targetOffsetY = { fullHeight -> -fullHeight }
            )
        }
    ) {
        MenuScreen(
            goToConfig = goToConfig,
            goToLeaderboard = goToLeaderboard,
            goToSettings = goToSettings,
            modifier = Modifier.fillMaxSize()
        )
    }
}
