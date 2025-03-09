package com.revechelizarondo.battleship.feature.config.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.revechelizarondo.battleship.core.domain.models.Ship
import com.revechelizarondo.battleship.core.ui.components.pixel.PixelButton
import com.revechelizarondo.battleship.core.ui.components.pixel.PixelContainerColors
import com.revechelizarondo.battleship.core.ui.icons.Add
import com.revechelizarondo.battleship.core.ui.icons.Remove
import com.revechelizarondo.battleship.core.ui.resources.Res
import com.revechelizarondo.battleship.core.ui.resources.add
import com.revechelizarondo.battleship.core.ui.resources.remove
import com.revechelizarondo.battleship.core.ui.theme.OnPixelButtonTextColor
import org.jetbrains.compose.resources.stringResource

private const val SHIP_SELECTOR_BUTTON_SIZE = 24
const val MIN_SHIP_QUANTITY = 0
const val MAX_SHIP_QUANTITY = 5

/**
 * A slot that presents the ship types and allows the user to select the quantity of each type in a game.
 *
 * @param shipTypes the list of ship types to be presented
 * @param onShipAdded callback that is called when the user adds a ship of the given type
 * @param onShipRemoved callback that is called when the user removes a ship of the given type
 */
@Composable
fun ShipQuantity(
    shipTypes: Map<Ship, Int>,
    onShipAdded: (Ship) -> Unit,
    onShipRemoved: (Ship) -> Unit
) {
    ShipSelector(
        modifier = Modifier.fillMaxSize(),
        shipTypes = shipTypes
    ) { ship, count ->

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PixelButton(
                enabled = true,
                onClick = { onShipAdded(ship) },
                cornerSize = 4,
                colors = PixelContainerColors.NConsoleCompanyPixelContainerColors
            ) {
                Icon(
                    imageVector = Add,
                    contentDescription = stringResource(Res.string.add),
                    tint = OnPixelButtonTextColor
                )
            }

            Text(modifier = Modifier.padding(15.dp), text = count.toString())

            PixelButton(
                enabled = true,
                onClick = { onShipRemoved(ship) },
                cornerSize = 4,
                colors = PixelContainerColors.NConsoleCompanyPixelContainerColors
            ) {
                Icon(
                    imageVector = Remove,
                    contentDescription = stringResource(Res.string.remove),
                    tint = OnPixelButtonTextColor
                )
            }
        }
    }
}