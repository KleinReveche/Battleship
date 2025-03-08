package com.revechelizarondo.battleship.core.ui.components.retro

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun RetroStarsEffect() {
    // A simplified version - in a real app this would animate randomly positioned stars
    Canvas(modifier = Modifier.fillMaxSize()) {
        val random = Random(0)
        repeat(100) {
            val x = random.nextFloat() * size.width
            val y = random.nextFloat() * size.height * 0.6f
            val radius = random.nextFloat() * 2f + 1f
            val alpha = random.nextFloat() * 0.8f + 0.2f
            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = radius,
                center = Offset(x, y)
            )
        }
    }
}