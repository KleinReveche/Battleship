package com.revechelizarondo.battleship.feature.play

import com.revechelizarondo.battleship.domain.game.domainGameModule
import org.koin.dsl.module

val playModule = module {
    includes(domainGameModule)
}