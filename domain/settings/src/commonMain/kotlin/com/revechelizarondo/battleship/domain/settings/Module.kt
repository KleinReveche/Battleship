package com.revechelizarondo.battleship.domain.settings

import com.revechelizarondo.battleship.data.settings.dataSettingsModule
import org.koin.dsl.module

val domainSettingsModule = module {
    includes(dataSettingsModule)
}