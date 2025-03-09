package com.revechelizarondo.battleship.core.ui.components.pixel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.revechelizarondo.battleship.core.ui.components.pixel.PixelContainerColors.Companion.GreyPixelContainerColors
import com.revechelizarondo.battleship.core.ui.components.pixel.PixelContainerColors.Companion.NConsoleCompanyPixelContainerColors

/**
 * From:
 * https://medium.com/@kiwi47/pixel-perfect-creating-a-retro-button-with-jetpack-compose-4b2697291fbb
 * https://gist.github.com/kiwi4747/4fe0590baa046ed3eff7e52e270f3824
 */
@Composable
fun PixelButton(
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    cornerSize: Int,
    pixelSize: Dp = 4.dp,
    colors: PixelContainerColors = NConsoleCompanyPixelContainerColors,
    content: @Composable () -> Unit,
) {
    Column {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()

        Box(
            modifier = Modifier
                .clickable(
                    enabled = enabled,
                    onClick = { onClick?.invoke() },
                    indication = null,
                    interactionSource = interactionSource
                )
                .drawBehind {
                    drawPixelContainer(
                        colors = if (enabled) colors else GreyPixelContainerColors,
                        pressedState = isPressed,
                        cornerSizeInitial = cornerSize,
                        pixelSize = pixelSize
                    )
                }
                .padding(
                    horizontal = (cornerSize.coerceAtLeast(1) + 1).toFloat() * pixelSize,
                    vertical = cornerSize.coerceAtLeast(1) * pixelSize
                )
        ) {
            content()
        }
    }
}