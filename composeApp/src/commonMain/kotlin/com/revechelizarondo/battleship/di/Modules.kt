package com.revechelizarondo.battleship.di

import com.revechelizarondo.battleship.BattleshipViewModel
import com.revechelizarondo.battleship.features.settings.SettingsScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val battleshipDatabasePlatformModule: Module

val repositorySharedModule = module {

}

val viewModelSharedModule = module {
    viewModelOf(::SettingsScreenViewModel)
    viewModelOf(::BattleshipViewModel)
}
