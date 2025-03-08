package com.revechelizarondo.battleship

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.revechelizarondo.battleship.core.domain.models.PreferenceKey
import com.revechelizarondo.battleship.core.domain.models.ShipRegistry
import com.revechelizarondo.battleship.core.domain.models.UIColorTypes
import com.revechelizarondo.battleship.core.domain.platform.Platforms
import com.revechelizarondo.battleship.core.domain.platform.getPlatform
import com.revechelizarondo.battleship.core.ui.theme.BattleshipTheme
import com.revechelizarondo.battleship.navigation.NavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.module.Module

@Composable
@Preview
fun Battleship(
    platformModule: Module = Module()
) {
    ShipRegistry.init()
    KoinApplication(
        application = {
            modules(appModule, platformModule)
        }
    ) {
        val vm: BattleshipViewModel = koinViewModel()
        val preferences by vm.preferences.collectAsState()

        val theme = preferences.firstOrNull { it.key == PreferenceKey.THEME.name }?.value
        val isDynamic = theme == null
        val isOled =
            preferences.firstOrNull { it.key == PreferenceKey.IS_OLED_MODE.name }?.value == "true"
        val isDarkMode = when (preferences.firstOrNull { it.key == PreferenceKey.IS_DARK_MODE.name }?.value) {
            "true" -> true
            "false" -> false
            else -> null
        }

        BattleshipTheme(
            dynamicColorAndroid = if (getPlatform().name == Platforms.Android) isDynamic else false,
            uiColorTypes = if (theme != null) UIColorTypes.valueOf(theme) else UIColorTypes.WildViolet,
            darkTheme = isDarkMode ?: isSystemInDarkTheme(),
            oled = isOled
        ) {
            Surface(Modifier.fillMaxSize()) { NavGraph() }
        }
    }
}