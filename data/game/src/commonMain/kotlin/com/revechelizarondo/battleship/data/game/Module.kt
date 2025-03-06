package com.revechelizarondo.battleship.data.game

import com.revechelizarondo.battleship.core.data.coreDataModule
import org.koin.dsl.module

val dataGameModule = module {
    includes(coreDataModule)
}