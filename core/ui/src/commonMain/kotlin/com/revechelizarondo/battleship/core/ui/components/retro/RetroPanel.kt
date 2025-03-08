package com.revechelizarondo.battleship.core.ui.components.retro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RetroPanel(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .border(width = 2.dp, color = Color.Cyan, shape = RoundedCornerShape(4.dp))
            .background(Color(0xFF000B32).copy(alpha = 0.7f), RoundedCornerShape(4.dp))
    ) {
        content()
    }
}