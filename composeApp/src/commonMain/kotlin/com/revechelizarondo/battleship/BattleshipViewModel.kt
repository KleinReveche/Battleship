package com.revechelizarondo.battleship

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revechelizarondo.battleship.domain.models.Preference
import com.revechelizarondo.battleship.domain.models.PreferenceKey
import com.revechelizarondo.battleship.domain.repository.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BattleshipViewModel(private val localRepository: LocalRepository) : ViewModel() {
    private val _preferences = MutableStateFlow<List<Preference>>(emptyList())
    val preferences: StateFlow<List<Preference>> = _preferences

    init {
        viewModelScope.launch {
            PreferenceKey.entries.forEach { key ->
                if (localRepository.getPreference(key) == null) {
                    localRepository.upsertPreference(key, key.defaultValue)
                }
            }

            localRepository.getPreferences().collect { prefs ->
                _preferences.value = prefs
            }
        }
    }
}