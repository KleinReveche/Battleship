package com.revechelizarondo.battleship

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revechelizarondo.battleship.core.domain.models.Preference
import com.revechelizarondo.battleship.core.domain.models.PreferenceKey
import com.revechelizarondo.battleship.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BattleshipViewModel(private val preferencesRepository: PreferencesRepository) : ViewModel() {
    private val _preferences = MutableStateFlow<List<Preference>>(emptyList())
    val preferences: StateFlow<List<Preference>> = _preferences

    init {
        viewModelScope.launch {
            PreferenceKey.entries.forEach { key ->
                if (preferencesRepository.getPreference(key) == null) {
                    preferencesRepository.upsertPreference(key, key.defaultValue)
                }
            }

            preferencesRepository.getPreferences().collect { prefs ->
                _preferences.value = prefs
            }
        }
    }
}