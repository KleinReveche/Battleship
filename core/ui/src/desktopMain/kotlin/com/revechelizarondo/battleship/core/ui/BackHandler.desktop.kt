package com.revechelizarondo.battleship.core.ui

import androidx.compose.runtime.Composable

// No BackHandler for Desktop
@Composable
actual fun BackHandler(onBack: () -> Unit) {
}