package com.revechelizarondo.battleship

import android.app.Application
import com.revechelizarondo.battleship.di.initKoin
import org.koin.android.ext.koin.androidContext

class BattleshipApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(androidContext(this@BattleshipApp))
    }
}