package com.revechelizarondo.battleship.feature.config

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ConfigGameSettingsRoute

fun NavGraphBuilder.configRoute(
    navigateUp: () -> Unit,
    goToGame: () -> Unit
) {
    composable<ConfigGameSettingsRoute>(
        enterTransition = {
            slideInVertically(
                animationSpec = tween(1000),
                initialOffsetY = { fullHeight -> fullHeight }
            )
        },
        exitTransition = {
            slideOutHorizontally(
                animationSpec = tween(500),
                targetOffsetX = { fullWidth -> fullWidth }
            )
        }
    ) {
        ConfigScreen(navigateUp, goToGame)
    }
}