package com.revechelizarondo.battleship.data.settings

import com.revechelizarondo.battleship.core.data.coreDataModule
import org.koin.dsl.module

val dataSettingsModule = module {
    includes(coreDataModule)
}