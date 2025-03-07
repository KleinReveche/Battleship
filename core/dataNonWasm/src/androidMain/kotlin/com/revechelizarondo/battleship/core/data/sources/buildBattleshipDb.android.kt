package com.revechelizarondo.battleship.core.data.sources

import android.content.Context
import androidx.room.Room
import com.revechelizarondo.battleship.core.data.source.BattleshipDatabase


fun buildBattleshipDb(context: Context): BattleshipDatabase =
    Room
        .databaseBuilder<BattleshipDatabase>(
            context = context.applicationContext,
            name = context.getDatabasePath(BattleshipDatabase.DATABASE_NAME).absolutePath,
        ).build()
