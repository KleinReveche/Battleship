package com.revechelizarondo.battleship.di

import com.revechelizarondo.battleship.data.repository.BrowserLocalRepositoryImpl
import com.revechelizarondo.battleship.data.sources.localStorage
import com.revechelizarondo.battleship.domain.repository.LocalRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.w3c.dom.Storage

actual val battleshipDatabasePlatformModule = module {
    single { localStorage }.bind<Storage>()

    singleOf(::BrowserLocalRepositoryImpl).bind<LocalRepository>()
}