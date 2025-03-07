package com.revechelizarondo.battleship.core.data

import org.koin.core.module.Module
import org.koin.dsl.module

val coreDataModule = module {
    includes(localStoragePlatformModule())
}

expect fun localStoragePlatformModule(): Module