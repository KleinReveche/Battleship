package com.revechelizarondo.battleship.feature.config.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.revechelizarondo.battleship.core.domain.models.Cell
import com.revechelizarondo.battleship.core.domain.models.GridCoordinates
import com.revechelizarondo.battleship.core.domain.models.Orientation
import com.revechelizarondo.battleship.core.domain.models.PlacedShip
import com.revechelizarondo.battleship.core.domain.models.Ship
import com.revechelizarondo.battleship.core.domain.models.ShipCell
import com.revechelizarondo.battleship.core.domain.models.WaterCell
import com.revechelizarondo.battleship.core.ui.components.DropItemZone

@Composable
fun PlayerGridDropZone(
    modifier: Modifier = Modifier,
    board: Array<Array<Cell>>,
    boardSize: Dp,
    cellSize: Dp,
    cellInBound: GridCoordinates?,
    getShipFromCell: (x: Int, y: Int) -> PlacedShip?,
    getShipCells: (gridCoordinates: GridCoordinates) -> Map<GridCoordinates, Cell>,
    isShipInBounds: (gridCoordinates: GridCoordinates) -> Boolean,
    updateCellInBound: (GridCoordinates) -> Unit,
    placeShip: (ship: Ship, shipIndex: Int, x: Int, y: Int) -> Unit,
    onGloballyPositioned: (dpSize: Dp) -> Unit,
    debug: Boolean = false
) {
    // Retro colors palette
    val waterColor = Color(0xFF0077BE) // Deeper ocean blue
    val gridLineColor = Color(0xFFD4AF37) // Gold for grid lines
    val hoverValidColor = Color(0xFF00FF00).copy(alpha = 0.4f) // Retro green
    val hoverInvalidColor = Color(0xFFFF0000).copy(alpha = 0.4f) // Retro red
    val labelBgColor = Color(0xFF3D5042).copy(alpha = 0.7f) // Earth tone from port

    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.background.copy(alpha = 0.2F))
    ) {
        // Radar sweep animation
        val infiniteTransition = rememberInfiniteTransition(label = "radar_sweep")
        val rotationAngle by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(6000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "radar_rotation"
        )

        // Draw grid with retro styling
        Column(
            modifier = modifier
                .size(boardSize)
                .padding(10.dp)
                .drawBehind {
                    // Draw radar circle
                    drawCircle(
                        color = gridLineColor.copy(alpha = 0.2f),
                        radius = size.minDimension / 2,
                        style = Stroke(width = 2.dp.toPx())
                    )
                    // Draw radar sweep
                    drawArc(
                        color = gridLineColor.copy(alpha = 0.4f),
                        startAngle = rotationAngle,
                        sweepAngle = 60f,
                        useCenter = true,
                        topLeft = Offset.Zero,
                        size = this.size
                    )
                }
        ) {
            for (row in 0 until board.size + 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    for (col in 0 until board.size + 1) {
                        val cell = if (row == 0 || col == 0) null else board[row - 1][col - 1]
                        val shipInCell =
                            if (cell is ShipCell) getShipFromCell(row - 1, col - 1) else null

                        DropItemZone<Pair<Ship, Int>>(
                            modifier = Modifier.weight(1f).padding(1.dp)
                        ) { isInBound, data ->
                            val cellCoords = GridCoordinates(row - 1, col - 1)

                            if (data != null) {
                                updateCellInBound(cellCoords)
                                LaunchedEffect(data) {
                                    val (ship, shipIndex) = data
                                    if (shipInCell == null && isShipInBounds(cellCoords)) {
                                        placeShip(ship, shipIndex, row - 1, col - 1)
                                    }
                                }
                            }

                            val borderColor: Color
                            var backgroundColor: Color

                            if (isInBound) {
                                updateCellInBound(cellCoords)
                                borderColor =
                                    if (isShipInBounds(cellCoords)) gridLineColor else Color.Red
                                backgroundColor =
                                    if (isShipInBounds(cellCoords)) hoverValidColor else hoverInvalidColor
                            } else {
                                borderColor = gridLineColor.copy(alpha = 0.6f)
                                backgroundColor = if (cell is ShipCell)
                                    Color.Gray.copy(alpha = 0.3f)
                                else
                                    waterColor.copy(alpha = 0.3f)
                            }

                            if (cellInBound != null) {
                                val shipCells = getShipCells(cellInBound)
                                backgroundColor = if (shipCells.keys.contains(cellCoords)) {
                                    if (shipCells.values.any { it is ShipCell })
                                        hoverInvalidColor
                                    else
                                        hoverValidColor
                                } else {
                                    backgroundColor
                                }
                            }

                            Box(
                                modifier = if (row == 0 || col == 0) {
                                    Modifier
                                        .background(labelBgColor)
                                        .aspectRatio(1f)
                                        .padding(2.dp)
                                        .onGloballyPositioned { onGloballyPositioned((it.size.width * 1.14).dp) }
                                } else {
                                    Modifier
                                        .border(
                                            width = 1.dp,
                                            color = borderColor,
                                            shape = RectangleShape
                                        )
                                        .background(backgroundColor)
                                        .aspectRatio(1f)
                                        .padding(2.dp)
                                        .onGloballyPositioned { onGloballyPositioned((it.size.width * 1.14).dp) }
                                        .shimmerEffect(enabled = cell is ShipCell)
                                },
                                contentAlignment = Alignment.Center
                            ) {
                                if (cell != null) {
                                    if (debug && cell is ShipCell && shipInCell is PlacedShip) {
                                        val o =
                                            if (shipInCell.orientation == Orientation.HORIZONTAL) "H" else "V"
                                        val shipCode = "${shipInCell.ship.shipCode}$o"

                                        Text(
                                            modifier = Modifier.fillMaxSize(),
                                            text = shipCode,
                                            textAlign = TextAlign.Center,
                                            color = Color.White
                                        )
                                    } else if (debug && cell is WaterCell) {
                                        Text(
                                            modifier = Modifier.fillMaxSize(),
                                            text = "    ",
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else {
                                    if (row == 0 && col == 0) {
                                        Text(
                                            text = " ",
                                            modifier = Modifier.fillMaxSize(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White
                                        )
                                    } else if (row == 0) {
                                        val alphabet = ('A'..'Z').toList()
                                        Text(
                                            text = "${alphabet[col - 1]}",
                                            modifier = Modifier.fillMaxSize(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White,
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold
                                        )
                                    } else {
                                        Text(
                                            text = "${(row - 1)}",
                                            modifier = Modifier.fillMaxSize(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White,
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Modifier.shimmerEffect(enabled: Boolean): Modifier {
    if (!enabled) return this

    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_alpha"
    )

    return this.drawWithCache {
        onDrawBehind {
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = alpha),
                        Color.White.copy(alpha = alpha / 3)
                    ),
                    center = center,
                    radius = size.minDimension
                )
            )
        }
    }
}