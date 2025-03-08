package com.revechelizarondo.battleship.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.revechelizarondo.battleship.core.domain.models.Orientation
import com.revechelizarondo.battleship.core.domain.models.Ship

@Composable
fun ShipSelector(
    modifier: Modifier = Modifier,
    shipTypes: Map<Ship, Int>,
    content: @Composable ((ship: Ship, count: Int) -> Unit)? = null
) {
    ElevatedCard(
        modifier = modifier
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(0.5f).height(300.dp).padding(vertical = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(shipTypes.entries.toList()) { (ship, count) ->
                Box(
                    modifier = Modifier.fillParentMaxHeight().padding(horizontal = 3.dp),
                ) {
                    Column(
                        modifier = Modifier.fillParentMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier.fillParentMaxHeight(0.5f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            ShipView(
                                type = ship,
                                orientation = Orientation.VERTICAL
                            )
                        }
                        if (content != null) {
                            Spacer(modifier = Modifier.size(10.dp))
                            content(ship, count)
                        }
                    }
                }
            }
        }
    }
}