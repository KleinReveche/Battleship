package com.revechelizarondo.battleship.core.ui.components.retro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RetroButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Yellow else Color.Cyan,
                shape = RoundedCornerShape(2.dp)
            )
            .background(
                if (isSelected) Color.Blue.copy(alpha = 0.6f)
                else Color.DarkGray.copy(alpha = 0.6f),
                RoundedCornerShape(2.dp)
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (enabled)
                (if (isSelected) Color.Yellow else Color.Cyan)
            else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}