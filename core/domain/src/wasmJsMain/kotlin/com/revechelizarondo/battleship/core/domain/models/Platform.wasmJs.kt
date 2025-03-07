package com.revechelizarondo.battleship.core.domain.models

data object WasmJsPlatform : Platform {
    override val name = Platforms.WasmJs
    override val version = "1.0.0"
}

actual fun getPlatform(): Platform = WasmJsPlatform