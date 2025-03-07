package com.revechelizarondo.battleship.core.domain.platform

interface Platform {
    val name: Platforms
    val version: String
}

enum class Platforms {
    Android,
    Linux,
    macOS,
    Unknown,
    WasmJs,
    Windows
}

expect fun getPlatform(): Platform