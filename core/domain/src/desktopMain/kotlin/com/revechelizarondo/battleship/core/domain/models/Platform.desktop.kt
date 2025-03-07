package com.revechelizarondo.battleship.core.domain.models

data object WindowsPlatform : Platform {
    override val name: Platforms = Platforms.Desktop
    override val version: String = System.getProperty("os.version") ?: "Unknown"
}

actual fun getPlatform(): Platform = WindowsPlatform