package com.revechelizarondo.battleship.feature.config

import com.revechelizarondo.battleship.core.domain.coreDomainModule
import org.koin.dsl.module

val configModule = module {
    includes(coreDomainModule)
}