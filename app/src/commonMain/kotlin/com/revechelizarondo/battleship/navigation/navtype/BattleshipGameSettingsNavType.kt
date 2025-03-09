package com.revechelizarondo.battleship.navigation.navtype

import com.revechelizarondo.battleship.core.domain.models.BattleshipGameSettings
import kotlinx.serialization.json.Json

class BattleshipGameSettingsNavType : JsonNavType<BattleshipGameSettings>() {
    private val json = Json { allowStructuredMapKeys = true }

    override fun fromJsonParse(value: String): BattleshipGameSettings {
        return json.decodeFromString(
            BattleshipGameSettings.serializer(),
            value.decodeHex().decodeToString()
        )
    }

    override fun BattleshipGameSettings.getJsonParse(): String = toGameSettingsStr(this)

    override fun serializeAsValue(value: BattleshipGameSettings): String = toGameSettingsStr(value)

    @OptIn(ExperimentalStdlibApi::class)
    private fun toGameSettingsStr(gameSettings: BattleshipGameSettings): String {
        return json.encodeToString(BattleshipGameSettings.serializer(), gameSettings)
            .encodeToByteArray().toHexString()
    }
}

fun String.decodeHex(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }

    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}