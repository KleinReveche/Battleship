package com.revechelizarondo.battleship.feature.config.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.revechelizarondo.battleship.core.domain.models.Orientation
import com.revechelizarondo.battleship.core.domain.models.Ship
import com.revechelizarondo.battleship.core.ui.components.ShipView

@Composable
fun ShipSelector(
    shipTypes: Map<Ship, Int>,
    modifier: Modifier = Modifier,
    content: @Composable ((ship: Ship, count: Int) -> Unit)? = null
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors().copy(
            containerColor = Color.Transparent.copy(alpha = 0.2F)
        ),
        shape = RectangleShape
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth().height(400.dp).padding(vertical = 30.dp),
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