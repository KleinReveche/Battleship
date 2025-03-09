package com.revechelizarondo.battleship.core.ui.components.pixel

import androidx.compose.ui.graphics.Color

/**
 * From:
 * https://medium.com/@kiwi47/pixel-perfect-creating-a-retro-button-with-jetpack-compose-4b2697291fbb
 * https://gist.github.com/kiwi4747/4fe0590baa046ed3eff7e52e270f3824
 */
data class PixelContainerColors(
    val backgroundColor: Color,
    val pressedBackgroundColor: Color,
    val borderColor: Color,
    val highlightColor: Color,
    val shadowColor: Color
) {
    companion object {
        val GreyPixelContainerColors = PixelContainerColors(
            backgroundColor = Color(0xFF808080), // Grey
            pressedBackgroundColor = Color(0xFFD3D3D3), // Light Grey
            borderColor = Color(0xFF474747), // Dim Grey
            highlightColor = Color(0xFFA9A9A9), // Dark Grey
            shadowColor = Color(0xFF696969) // Charcoal Grey
        )
        val NConsoleCompanyPixelContainerColors = PixelContainerColors(
            backgroundColor = Color(0xFF8bac0f),
            pressedBackgroundColor = Color(0xFFc2ea1b),
            borderColor = Color(0xFF0f380f),
            highlightColor = Color(0xFF9bbc0f),
            shadowColor = Color(0xFF306230)
        )
    }
}