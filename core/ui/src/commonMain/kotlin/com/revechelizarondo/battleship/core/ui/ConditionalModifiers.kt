package com.revechelizarondo.battleship.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.conditionalBorder(
    condition: Boolean,
    color: Color,
    width: Dp
): Modifier = if (condition) border(width, color) else this

fun Modifier.conditionalBackground(
    condition: Boolean,
    color: Color
): Modifier = if (condition) background(color) else this