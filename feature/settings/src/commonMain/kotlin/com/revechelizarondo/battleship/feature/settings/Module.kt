package com.revechelizarondo.battleship.feature.settings

import com.revechelizarondo.battleship.core.domain.coreDomainModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    includes(coreDomainModule)

    viewModel { SettingsViewModel(get()) }
}