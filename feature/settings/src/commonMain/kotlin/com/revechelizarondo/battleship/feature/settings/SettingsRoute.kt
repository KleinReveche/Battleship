package com.revechelizarondo.battleship.feature.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute


fun NavGraphBuilder.settingsRoute(
    returnToMenu: () -> Unit,
) {
    composable<SettingsRoute> {
        Settings(
            returnToMenu = returnToMenu,
            modifier = Modifier.fillMaxSize()
        )
    }
}