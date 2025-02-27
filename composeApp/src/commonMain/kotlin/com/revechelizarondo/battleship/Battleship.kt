package com.revechelizarondo.battleship

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.revechelizarondo.battleship.domain.models.PreferenceKey
import com.revechelizarondo.battleship.domain.models.UIColorTypes
import com.revechelizarondo.battleship.features.common.theme.BattleshipTheme
import com.revechelizarondo.battleship.features.navigation.Navigation
import com.revechelizarondo.battleship.platform.getPlatform
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun Battleship() {
    val vm: BattleshipViewModel = koinViewModel()
    val preferences by vm.preferences.collectAsState()

    val theme = preferences.firstOrNull { it.key == PreferenceKey.THEME.name }?.value
    val isDynamic = theme == null
    val isOled =
        preferences.firstOrNull { it.key == PreferenceKey.IS_OLED_MODE.name }?.value == "true"
    val isDarkMode =
        when (preferences.firstOrNull { it.key == PreferenceKey.IS_DARK_MODE.name }?.value) {
            "true" -> true
            "false" -> false
            else -> null
        }

    BattleshipTheme(
        dynamicColorAndroid = if (getPlatform().name.startsWith("Android")) isDynamic else false,
        uiColorTypes = if (theme != null) UIColorTypes.valueOf(theme) else UIColorTypes.WildViolet,
        darkTheme = isDarkMode ?: isSystemInDarkTheme(),
        oled = isOled
    ) {
        Navigation()
    }
}