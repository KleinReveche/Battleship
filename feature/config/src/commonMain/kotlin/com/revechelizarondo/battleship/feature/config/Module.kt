package com.revechelizarondo.battleship.feature.config

import com.revechelizarondo.battleship.domain.game.domainGameModule
import org.koin.dsl.module

val configModule = module {
    includes(domainGameModule)
}