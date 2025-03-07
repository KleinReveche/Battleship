package com.revechelizarondo.battleship.core.domain.models

import android.os.Build

data object AndroidPlatform : Platform {
    override val name = Platforms.Android
    override val version: String = Build.VERSION.RELEASE
}

actual fun getPlatform(): Platform = AndroidPlatform