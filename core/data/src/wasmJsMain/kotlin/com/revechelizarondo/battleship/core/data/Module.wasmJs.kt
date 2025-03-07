package com.revechelizarondo.battleship.core.data

import com.revechelizarondo.battleship.core.data.repository.LocalStoragePreferencesRepositoryImpl
import com.revechelizarondo.battleship.core.data.source.localStorage
import com.revechelizarondo.battleship.core.domain.repository.PreferencesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.w3c.dom.Storage

actual fun localStoragePlatformModule() = module {
    single { localStorage }.bind<Storage>()

    singleOf(::LocalStoragePreferencesRepositoryImpl).bind<PreferencesRepository>()
}