package com.revechelizarondo.battleship.data.util

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

object Converters {

    @TypeConverter
    fun fromTimestamp(value: String) = Instant.parse(value)

    @TypeConverter
    fun dateToTimestamp(date: Instant) = date.toString()

    // TODO: Add converters for board game
}