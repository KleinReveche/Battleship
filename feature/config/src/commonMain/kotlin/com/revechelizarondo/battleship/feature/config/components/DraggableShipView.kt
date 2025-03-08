package com.revechelizarondo.battleship.feature.config.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.revechelizarondo.battleship.core.domain.models.Orientation
import com.revechelizarondo.battleship.core.domain.models.Ship
import com.revechelizarondo.battleship.core.ui.components.DraggableContent
import com.revechelizarondo.battleship.core.ui.components.ShipView

/**
 * A draggable ship.
 *
 * @param shipTypes the list of ship types to be presented
 * @param startDragging callback that is called when the user starts dragging a ship
 * @param stopDragging callback that is called when the user stops dragging a ship
 * @param modifier the modifier
 */
@Composable
fun DraggableShipView(
    modifier: Modifier = Modifier,
    shipTypes: Map<Ship, Int>,
    startDragging: (ship: Ship, orientation: Orientation) -> Unit,
    stopDragging: () -> Unit
) {
    ElevatedCard(modifier = modifier) {
        if (shipTypes.any { it.value > 0 }) {
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items(
                    items = shipTypes.entries.filter { it.value > 0 }.toList(),
                    key = { it.key } // Use ship type as stable key
                ) { (ship, count) ->
                    // Key orientation state to specific ship
                    var orientation by remember(ship) { mutableStateOf(Orientation.VERTICAL) }

                    Box(
                        modifier = Modifier.padding(horizontal = 3.dp),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = count.toString(),
                                modifier = Modifier.padding(7.dp)
                            )

                            val shipIndex = count - 1
                            DraggableContent(
                                dataToDrop = Pair(ship, shipIndex),
                                startDragging = { startDragging(ship, orientation) },
                                stopDragging = stopDragging,
                                additionalModifier = {
                                    this.pointerInput(ship) {
                                        detectTapGestures(
                                            onTap = {
                                                orientation =
                                                    if (orientation == Orientation.VERTICAL) {
                                                        Orientation.HORIZONTAL
                                                    } else {
                                                        Orientation.VERTICAL
                                                    }
                                            }
                                        )
                                    }
                                }
                            ) {
                                ShipView(
                                    type = ship,
                                    orientation = orientation
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Text(
                text = "No ships available.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}