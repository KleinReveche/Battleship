package com.revechelizarondo.battleship.navigation.navtype

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write

abstract class JsonNavType<T> : NavType<T>(isNullableAllowed = false) {
    abstract fun fromJsonParse(value: String): T
    abstract fun T.getJsonParse(): String

    override fun parseValue(value: String): T = fromJsonParse(value)
    override fun put(bundle: SavedState, key: String, value: T) {
        bundle.write { putString(key, value.getJsonParse()) }
    }


    override fun get(bundle: SavedState, key: String): T? {
        return bundle.read { parseValue(getString(key)) }
    }
}