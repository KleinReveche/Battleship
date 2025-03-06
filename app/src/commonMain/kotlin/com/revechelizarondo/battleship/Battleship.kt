package com.revechelizarondo.battleship

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.revechelizarondo.battleship.feature.menu.MenuScreen
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
        MaterialTheme {
            MenuScreen()
        }
    }
}