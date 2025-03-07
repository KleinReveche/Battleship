package com.revechelizarondo.battleship.feature.leaderboard

import com.revechelizarondo.battleship.core.domain.coreDomainModule
import org.koin.dsl.module

val leaderboardModule = module {
    includes(coreDomainModule)
}