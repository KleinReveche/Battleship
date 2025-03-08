package com.revechelizarondo.battleship.feature.config

import com.revechelizarondo.battleship.core.domain.coreDomainModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val configModule = module {
    includes(coreDomainModule)

    viewModel { ConfigScreenViewModel() }
    //viewModel { ConfigPlayerShipsViewModel(it.get(), it.get()) }
}