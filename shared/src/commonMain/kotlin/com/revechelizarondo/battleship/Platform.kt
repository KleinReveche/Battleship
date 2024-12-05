package com.revechelizarondo.battleship

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform