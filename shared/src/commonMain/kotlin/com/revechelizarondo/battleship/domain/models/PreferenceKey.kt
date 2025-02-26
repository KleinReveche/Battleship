package com.revechelizarondo.battleship.domain.models

enum class PreferenceKey(val type: PreferenceType, val defaultValue: String? = null) {
    THEME(PreferenceType.UI_COLOR_TYPE, UIColorTypes.WildViolet.name),
    IS_OLED_MODE(PreferenceType.BOOLEAN, "false"),
    IS_DARK_MODE(PreferenceType.BOOLEAN, null),
}

enum class PreferenceType {
    STRING,
    BOOLEAN,
    INTEGER,
    FLOAT,
    LONG,
    UI_COLOR_TYPE,
}