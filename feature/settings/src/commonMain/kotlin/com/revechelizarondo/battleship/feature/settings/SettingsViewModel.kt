package com.revechelizarondo.battleship.feature.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revechelizarondo.battleship.core.domain.models.PreferenceKey
import com.revechelizarondo.battleship.core.domain.models.ThemeOptions
import com.revechelizarondo.battleship.core.domain.models.UIColorTypes
import com.revechelizarondo.battleship.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    var isDarkMode: Boolean? by mutableStateOf(null)
    var isOled by mutableStateOf(false)
    var selectedTheme: UIColorTypes? by mutableStateOf(UIColorTypes.WildViolet)

    init {
        viewModelScope.launch {
            isDarkMode = when (preferencesRepository.getPreference(PreferenceKey.IS_DARK_MODE)?.value) {
                "true" -> true
                "false" -> false
                else -> null
            }
            isOled = preferencesRepository.getPreference(PreferenceKey.IS_OLED_MODE)?.value == "true"
            selectedTheme =
                UIColorTypes.valueOf(preferencesRepository.getPreference(PreferenceKey.THEME)?.value!!)
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
            preferencesRepository.upsertPreference(PreferenceKey.IS_DARK_MODE, isDarkMode.toString())
        }
    }

    fun setIsOled(selected: String) {
        isOled = selected == "Yes"

        viewModelScope.launch {
            preferencesRepository.upsertPreference(PreferenceKey.IS_OLED_MODE, isOled.toString())
        }
    }

    fun setTheme(selectedTheme: UIColorTypes?) {
        this.selectedTheme = selectedTheme

        viewModelScope.launch {
            preferencesRepository.upsertPreference(PreferenceKey.THEME, selectedTheme?.name)
        }
    }
}