package com.revechelizarondo.battleship

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.revechelizarondo.battleship.feature.menu.MenuScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        MenuScreen()
    }
}