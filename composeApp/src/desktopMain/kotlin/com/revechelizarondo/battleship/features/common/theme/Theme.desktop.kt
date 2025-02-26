package com.revechelizarondo.battleship.features.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.revechelizarondo.battleship.domain.models.UIColorTypes

@Composable
actual fun BattleshipTheme(
    dynamicColorAndroid: Boolean,
    uiColorTypes: UIColorTypes,
    darkTheme: Boolean,
    oled: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (darkTheme) darkColorScheme(uiColorTypes, oled) else lightColorScheme(uiColorTypes)
    MaterialTheme(colorScheme = colorScheme, content = content)
}