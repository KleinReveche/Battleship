package com.revechelizarondo.battleship.core.data.sources

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.revechelizarondo.battleship.core.data.source.BattleshipDatabase

fun buildBattleshipDb(): BattleshipDatabase =
    Room
        .databaseBuilder<BattleshipDatabase>(name = BattleshipDatabase.getDatabaseLocation())
        .setDriver(BundledSQLiteDriver())
        .build()
