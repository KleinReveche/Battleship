package com.revechelizarondo.battleship.core.ui.components.pixel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

/**
 * From:
 * https://medium.com/@kiwi47/pixel-perfect-creating-a-retro-button-with-jetpack-compose-4b2697291fbb
 * https://gist.github.com/kiwi4747/4fe0590baa046ed3eff7e52e270f3824
 */
fun DrawScope.drawPixelContainer(
    colors: PixelContainerColors,
    pixelSize: Dp = 4.dp,
    pressedState: Boolean = false,
    cornerSizeInitial: Int = 1,
) {
    val unit = pixelSize.toPx()
    val cornerSize =
        cornerSizeInitial.coerceAtMost((min(size.width, size.height) / unit / 2).toInt())

    drawBackground(
        unit,
        if (pressedState) colors.pressedBackgroundColor else colors.backgroundColor,
        cornerSize,
    )
    drawBorder(unit, cornerSize, colors.borderColor)
    drawHighlight(
        unit, cornerSize,
        if (pressedState) colors.shadowColor else colors.highlightColor
    )
    drawShadow(
        unit, cornerSize,
        if (pressedState) colors.highlightColor else colors.shadowColor
    )
}

/*
 * From:
 * https://medium.com/@kiwi47/pixel-perfect-creating-a-retro-button-with-jetpack-compose-4b2697291fbb
 * https://gist.github.com/kiwi4747/4fe0590baa046ed3eff7e52e270f3824
 */
fun DrawScope.drawBackground(
    unit: Float,
    borderColor: Color,
    cornerSize: Int
) {

    val horizontalBorderSize = this.size.width - (cornerSize + 1) * 2 * unit

    val path = Path().apply {
        moveTo((cornerSize + 1) * unit, 0f)
        lineTo(horizontalBorderSize, 0f)
        //top-right corner
        for (i in cornerSize downTo 0) {
            lineTo(
                this@drawBackground.size.width - (i * unit),
                (cornerSize - i) * unit
            )
            lineTo(
                this@drawBackground.size.width - (i * unit),
                (cornerSize - i + 1) * unit
            )
        }
        //right
        lineTo(size.width, size.height - (cornerSize * unit))
        //bottom-right corner
        for (i in 0..cornerSize) {
            lineTo(
                this@drawBackground.size.width - (i) * unit,
                this@drawBackground.size.height - ((cornerSize - i) * unit)
            )
            lineTo(
                this@drawBackground.size.width - (i + 1) * unit,
                this@drawBackground.size.height - ((cornerSize - i) * unit)
            )
        }
        lineTo((cornerSize + 1) * unit, size.height)
        //bottom-left corner
        for (i in cornerSize downTo 0) {
            lineTo(
                (i * unit),
                this@drawBackground.size.height - ((cornerSize - i) * unit)
            )
            lineTo(
                (i * unit),
                this@drawBackground.size.height - ((cornerSize - i + 1) * unit)
            )
        }
        //left
        lineTo(0f, size.height - (cornerSize * unit))
        //top-left corner
        for (i in 0..cornerSize) {
            lineTo(
                ((i) * unit),
                (cornerSize - i) * unit
            )
            lineTo(
                ((i + 1) * unit),
                (cornerSize - i) * unit
            )
        }
        close()
    }
    drawPath(path, borderColor)
}

/**
 * From:
 * https://medium.com/@kiwi47/pixel-perfect-creating-a-retro-button-with-jetpack-compose-4b2697291fbb
 * https://gist.github.com/kiwi4747/4fe0590baa046ed3eff7e52e270f3824
 */
private fun DrawScope.drawBorder(
    unit: Float,
    cornerSize: Int,
    borderColor: Color,
) {

    // Function to generate border segments for a given corner size
    fun getBorderSegments(cornerSize: Int): List<Segment> {
        val segments = mutableListOf<Segment>()

        val horizontalBorderSize = this.size.width - (cornerSize) * 2 * unit
        val verticalBorderSize = this.size.height - (cornerSize) * 2 * unit

        // Add top, bottom, left, and right border segments
        segments.add(
            Segment(
                Offset(cornerSize * unit, 0f),
                Size(horizontalBorderSize, unit)
            )
        )
        segments.add(
            Segment(
                Offset(cornerSize * unit, this.size.height - unit),
                Size(horizontalBorderSize, unit)
            )
        )
        segments.add(
            Segment(
                Offset(0f, cornerSize * unit),
                Size(unit, verticalBorderSize)
            )
        )
        segments.add(
            Segment(
                Offset(this.size.width - unit, cornerSize * unit),
                Size(unit, verticalBorderSize)
            )
        )
        // Add corner segments using loops
        for (i in 1..cornerSize) {
            for (j in 1..cornerSize) {
                if (i + j == cornerSize) { // Condition for corner segments
                    // Top-left corner
                    segments.add(
                        Segment(
                            Offset(i * unit, j * unit),
                            Size(unit, unit)
                        )
                    )
                    // Top-right corner
                    segments.add(
                        Segment(
                            Offset(
                                this.size.width - (i + 1) * unit,
                                j * unit
                            ), Size(unit, unit)
                        )
                    )
                    // Bottom-right corner
                    segments.add(
                        Segment(
                            Offset(
                                this.size.width - (i + 1) * unit,
                                this.size.height - (j + 1) * unit
                            ), Size(unit, unit)
                        )
                    )
                    // Bottom-left corner
                    segments.add(
                        Segment(
                            Offset(
                                i * unit,
                                this.size.height - (j + 1) * unit
                            ), Size(unit, unit)
                        )
                    )
                }
            }
        }


        return segments
    }

    // Draw the border
    drawSegments(getBorderSegments(cornerSize), borderColor)
}

/**
 * From:
 * https://medium.com/@kiwi47/pixel-perfect-creating-a-retro-button-with-jetpack-compose-4b2697291fbb
 * https://gist.github.com/kiwi4747/4fe0590baa046ed3eff7e52e270f3824
 */
private fun DrawScope.drawHighlight(
    unit: Float,
    cornerSize: Int,
    borderColor: Color,
) {

    // Function to generate border segments for a given corner size
    fun getBorderSegments(cornerSize: Int): List<Segment> {
        val segments = mutableListOf<Segment>()

        val horizontalBorderSize = this.size.width - (cornerSize + 1) * 2 * unit

        // Add top segment
        segments.add(
            Segment(
                Offset((cornerSize + 1) * unit, unit),
                Size(horizontalBorderSize, unit)
            )
        )
        // Add corner segments using loops
        for (i in 0..cornerSize) {
            for (j in 1..cornerSize) {
                if (i + j == cornerSize) { // Condition for corner segments
                    // Top-left corner
                    segments.add(
                        Segment(
                            Offset(i * unit + unit, j * unit),
                            Size(unit, unit)
                        )
                    )
                }
            }
        }
        return segments
    }

    drawSegments(getBorderSegments(cornerSize), borderColor)
}

/**
 * From:
 * https://medium.com/@kiwi47/pixel-perfect-creating-a-retro-button-with-jetpack-compose-4b2697291fbb
 * https://gist.github.com/kiwi4747/4fe0590baa046ed3eff7e52e270f3824
 */
private fun DrawScope.drawShadow(
    unit: Float,
    cornerSize: Int,
    borderColor: Color,
) {
    // Function to generate border segments for a given corner size
    fun getBorderSegments(cornerSize: Int): List<Segment> {
        val segments = mutableListOf<Segment>()

        val horizontalBorderSize = this.size.width - (cornerSize + 1) * 2 * unit

        // Add bottom segment
        segments.add(
            Segment(
                Offset((cornerSize + 1) * unit, this.size.height - 2 * unit),
                Size(horizontalBorderSize, unit)
            )
        )
        // Add corner segments using loops
        for (i in 0..<cornerSize) {
            for (j in 0..cornerSize) {
                if (i + j == cornerSize) { // Condition for corner segments

                    // Bottom-right corner
                    segments.add(
                        Segment(
                            Offset(
                                this.size.width - (i + 2) * unit,
                                this.size.height - (j + 1) * unit
                            ), Size(unit, unit)
                        )
                    )
                }
            }
        }


        return segments
    }

    // Draw the border
    drawSegments(getBorderSegments(cornerSize), borderColor)
}

/**
 * Define a data class to represent a border segment
 *
 * From:
 * https://gist.github.com/kiwi4747/4fe0590baa046ed3eff7e52e270f3824
 */
private data class Segment(val topLeft: Offset, val size: Size)

/**
 * Function to draw a list of border segments
 *
 * From:
 * https://gist.github.com/kiwi4747/4fe0590baa046ed3eff7e52e270f3824
 */
private fun DrawScope.drawSegments(segments: List<Segment>, color: Color) {
    segments.forEach { segment ->
        drawRect(
            color = color,
            topLeft = segment.topLeft,
            size = segment.size
        )
    }
}