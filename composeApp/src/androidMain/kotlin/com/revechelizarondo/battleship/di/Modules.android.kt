package com.revechelizarondo.battleship.di

import com.revechelizarondo.battleship.data.repository.RoomLocalRepositoryImpl
import com.revechelizarondo.battleship.data.source.buildBattleshipDb
import com.revechelizarondo.battleship.domain.repository.LocalRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val battleshipDatabasePlatformModule = module {
    single { buildBattleshipDb(get()) }

    singleOf(::RoomLocalRepositoryImpl).bind<LocalRepository>()
}