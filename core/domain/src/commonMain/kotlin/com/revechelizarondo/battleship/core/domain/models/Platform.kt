package com.revechelizarondo.battleship.core.domain.models

interface Platform {
    val name: Platforms
    val version: String
}

enum class Platforms {
    Android,
    Desktop,
    WasmJs
}

expect fun getPlatform(): Platform