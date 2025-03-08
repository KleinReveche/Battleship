package com.revechelizarondo.battleship.core.ui

import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

fun isScreenCompact(windowSizeClass: WindowSizeClass): Boolean {
    return when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.COMPACT
        WindowWidthSizeClass.COMPACT -> windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT
        else -> false
    }
}