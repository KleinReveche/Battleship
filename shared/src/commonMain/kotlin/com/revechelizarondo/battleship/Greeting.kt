package com.revechelizarondo.battleship

import com.revechelizarondo.battleship.platform.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}