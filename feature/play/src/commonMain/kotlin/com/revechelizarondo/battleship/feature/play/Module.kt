package com.revechelizarondo.battleship.feature.play

import com.revechelizarondo.battleship.core.domain.coreDomainModule
import org.koin.dsl.module

val playModule = module {
    includes(coreDomainModule)
}