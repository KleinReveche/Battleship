package com.revechelizarondo.battleship.core.data

import com.revechelizarondo.battleship.core.data.repository.RoomPreferencesRepositoryImpl
import com.revechelizarondo.battleship.core.data.sources.buildBattleshipDb
import com.revechelizarondo.battleship.core.domain.repository.PreferencesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun localStoragePlatformModule() = module {
    single { buildBattleshipDb(get()) }

    singleOf(::RoomPreferencesRepositoryImpl).bind<PreferencesRepository>()
}