package com.revechelizarondo.battleship.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revechelizarondo.battleship.core.domain.models.BattleshipClassic
import com.revechelizarondo.battleship.core.domain.models.Orientation
import com.revechelizarondo.battleship.core.domain.models.Ship
import com.revechelizarondo.battleship.core.ui.resources.Res
import com.revechelizarondo.battleship.core.ui.resources.ship_battleship_h
import com.revechelizarondo.battleship.core.ui.resources.ship_battleship_v
import com.revechelizarondo.battleship.core.ui.resources.ship_carrier_h
import com.revechelizarondo.battleship.core.ui.resources.ship_carrier_v
import com.revechelizarondo.battleship.core.ui.resources.ship_cruiser_h
import com.revechelizarondo.battleship.core.ui.resources.ship_cruiser_v
import com.revechelizarondo.battleship.core.ui.resources.ship_destroyer_h
import com.revechelizarondo.battleship.core.ui.resources.ship_destroyer_v
import com.revechelizarondo.battleship.core.ui.resources.ship_submarine_h
import com.revechelizarondo.battleship.core.ui.resources.ship_submarine_v
import org.jetbrains.compose.resources.painterResource

@Composable
fun ShipView(
    modifier: Modifier = Modifier,
    type: Ship,
    orientation: Orientation
) {
    val shipImage = painterResource(
        if (orientation == Orientation.HORIZONTAL) {
            when (type) {
                BattleshipClassic.CARRIER -> Res.drawable.ship_carrier_h
                BattleshipClassic.BATTLESHIP -> Res.drawable.ship_battleship_h
                BattleshipClassic.CRUISER -> Res.drawable.ship_cruiser_h
                BattleshipClassic.DESTROYER -> Res.drawable.ship_destroyer_h
                BattleshipClassic.SUBMARINE -> Res.drawable.ship_submarine_h
                else -> throw IllegalArgumentException("Invalid ship type")
            }
        } else {
            when (type) {
                BattleshipClassic.CARRIER -> Res.drawable.ship_carrier_v
                BattleshipClassic.BATTLESHIP -> Res.drawable.ship_battleship_v
                BattleshipClassic.CRUISER -> Res.drawable.ship_cruiser_v
                BattleshipClassic.DESTROYER -> Res.drawable.ship_destroyer_v
                BattleshipClassic.SUBMARINE -> Res.drawable.ship_submarine_v
                else -> throw IllegalArgumentException("Invalid ship type")
            }
        }
    )

    Image(
        painter = shipImage,
        contentDescription = type.shipName,
        modifier = modifier
    )
}