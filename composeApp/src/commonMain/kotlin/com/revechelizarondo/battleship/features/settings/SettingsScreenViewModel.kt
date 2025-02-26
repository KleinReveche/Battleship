package com.revechelizarondo.battleship.features.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revechelizarondo.battleship.domain.models.PreferenceKey
import com.revechelizarondo.battleship.domain.models.ThemeOptions
import com.revechelizarondo.battleship.domain.models.UIColorTypes
import com.revechelizarondo.battleship.domain.repository.LocalRepository
import kotlinx.coroutines.launch

class SettingsScreenViewModel(private val localRepository: LocalRepository) : ViewModel() {
    var isDarkMode: Boolean? by mutableStateOf(null)
    var isOled by mutableStateOf(false)
    var selectedTheme: UIColorTypes? by mutableStateOf(UIColorTypes.WildViolet)

    init {
        viewModelScope.launch {
            isDarkMode = when (localRepository.getPreference(PreferenceKey.IS_DARK_MODE)?.value) {
                "true" -> true
                "false" -> false
                else -> null
            }
            isOled = localRepository.getPreference(PreferenceKey.IS_OLED_MODE)?.value == "true"
            selectedTheme =
                UIColorTypes.valueOf(localRepository.getPreference(PreferenceKey.THEME)?.value!!)
        }
    }

    fun setIsDark(selectedTheme: String) {
        when (selectedTheme) {
            ThemeOptions.Dark.name -> {
                isDarkMode = true
            }

            ThemeOptions.Light.name -> {
                isDarkMode = false
            }

            ThemeOptions.System.name -> {
                isDarkMode = null
            }
        }

        viewModelScope.launch {
            localRepository.upsertPreference(PreferenceKey.IS_DARK_MODE, isDarkMode.toString())
        }
    }

    fun setIsOled(selected: String) {
        isOled = selected == "Yes"

        viewModelScope.launch {
            localRepository.upsertPreference(PreferenceKey.IS_OLED_MODE, isOled.toString())
        }
    }

    fun setTheme(selectedTheme: UIColorTypes?) {
        this.selectedTheme = selectedTheme

        viewModelScope.launch {
            localRepository.upsertPreference(PreferenceKey.THEME, selectedTheme?.name)
        }
    }
}