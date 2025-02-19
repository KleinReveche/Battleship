package com.revechelizarondo.battleship

import androidx.compose.runtime.Composable
import com.revechelizarondo.battleship.features.common.theme.BattleshipTheme
import com.revechelizarondo.battleship.features.navigation.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun Battleship() {
    BattleshipTheme {
        Navigation()
    }
}