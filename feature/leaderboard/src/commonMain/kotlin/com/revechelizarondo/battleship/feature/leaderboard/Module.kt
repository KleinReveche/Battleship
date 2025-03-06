package com.revechelizarondo.battleship.feature.leaderboard

import com.revechelizarondo.battleship.domain.game.domainGameModule
import org.koin.dsl.module

val leaderboardModule = module {
    includes(domainGameModule)
}