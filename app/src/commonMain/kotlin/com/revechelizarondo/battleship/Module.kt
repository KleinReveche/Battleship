package com.revechelizarondo.battleship

import com.revechelizarondo.battleship.core.data.coreDataModule
import com.revechelizarondo.battleship.feature.config.configModule
import com.revechelizarondo.battleship.feature.leaderboard.leaderboardModule
import com.revechelizarondo.battleship.feature.menu.menuModule
import com.revechelizarondo.battleship.feature.play.playModule
import com.revechelizarondo.battleship.feature.settings.settingsModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(
        leaderboardModule,
        configModule,
        menuModule,
        playModule,
        settingsModule,
        coreDataModule
    )

    viewModel { BattleshipViewModel(get()) }
}