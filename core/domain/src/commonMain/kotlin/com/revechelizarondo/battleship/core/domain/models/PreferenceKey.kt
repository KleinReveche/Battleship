package com.revechelizarondo.battleship.core.domain.models

enum class PreferenceKey(val type: PreferenceType, val defaultValue: String? = null) {
    THEME(PreferenceType.UI_COLOR_TYPE, UIColorTypes.WildViolet.name),
    IS_OLED_MODE(PreferenceType.BOOLEAN, "false"),
    IS_DARK_MODE(PreferenceType.BOOLEAN, null),
}

fun PreferenceKey.getPreferenceName(): String = "Preference.${this.name}"

enum class PreferenceType {
    STRING,
    BOOLEAN,
    INTEGER,
    FLOAT,
    LONG,
    UI_COLOR_TYPE,
}