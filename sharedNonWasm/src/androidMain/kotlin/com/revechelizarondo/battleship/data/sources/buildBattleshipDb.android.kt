package com.revechelizarondo.battleship.data.source


fun buildBattleshipDb(context: Context): BattleshipDatabase =
    Room
        .databaseBuilder<BattleshipDatabase>(
            context = context.applicationContext,
            name = context.getDatabasePath(BattleshipDatabase.DATABASE_NAME).absolutePath,
        ).build()
