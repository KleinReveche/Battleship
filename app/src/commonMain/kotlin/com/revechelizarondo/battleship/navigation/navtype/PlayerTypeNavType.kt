package com.revechelizarondo.battleship.navigation.navtype

import com.revechelizarondo.battleship.core.domain.models.PlayerType

class PlayerTypeNavType : JsonNavType<PlayerType>() {
    override fun fromJsonParse(value: String): PlayerType = PlayerType.valueOf(value)
    override fun PlayerType.getJsonParse(): String = name
}