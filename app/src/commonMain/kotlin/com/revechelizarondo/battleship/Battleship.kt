package com.revechelizarondo.battleship

import androidx.compose.runtime.Composable
import com.revechelizarondo.battleship.core.ui.theme.BattleshipTheme
import com.revechelizarondo.battleship.navigation.NavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.module.Module

@Composable
@Preview
fun Battleship(
    platformModule: Module = Module()
) {
    KoinApplication(
        application = {
            modules(appModule, platformModule)
        }
    ) {
        BattleshipTheme {
            NavGraph()
        }
    }
}