package com.revechelizarondo.battleship.core.domain.models

enum class PlayerType {
    PLAYER_1,
    PLAYER_2;

    fun opponent(): PlayerType {
        return when (this) {
            PLAYER_1 -> PLAYER_2
            PLAYER_2 -> PLAYER_1
        }
    }
}