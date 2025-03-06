package com.revechelizarondo.battleship.domain.game

import com.revechelizarondo.battleship.data.game.dataGameModule
import com.revechelizarondo.battleship.data.settings.dataSettingsModule
import org.koin.dsl.module

val domainGameModule = module {
    includes(
        dataGameModule,
        dataSettingsModule
    )
}