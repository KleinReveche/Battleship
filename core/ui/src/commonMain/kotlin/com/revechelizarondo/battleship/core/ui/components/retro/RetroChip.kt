package com.revechelizarondo.battleship.core.ui.components.retro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RetroChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Yellow else Color.Cyan,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                if (isSelected) Color.Blue.copy(alpha = 0.6f)
                else Color.Transparent,
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelected) {
            Icon(
                Icons.Default.Done,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(16.dp).padding(end = 4.dp)
            )
        }
        Text(
            text = text,
            color = if (isSelected) Color.Yellow else Color.Cyan
        )
    }
}
