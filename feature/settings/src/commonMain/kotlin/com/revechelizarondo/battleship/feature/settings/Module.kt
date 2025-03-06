package com.revechelizarondo.battleship.feature.settings

import com.revechelizarondo.battleship.domain.settings.domainSettingsModule
import org.koin.dsl.module

val settingsModule = module {
    includes(domainSettingsModule)
}