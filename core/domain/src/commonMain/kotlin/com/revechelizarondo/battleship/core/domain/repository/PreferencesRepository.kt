package com.revechelizarondo.battleship.core.domain.repository

import com.revechelizarondo.battleship.core.domain.models.PreferenceKey
import com.revechelizarondo.battleship.core.domain.models.Preference
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun upsertPreference(key: PreferenceKey, value: String?)
    suspend fun getPreference(key: PreferenceKey): Preference?
    fun getPreferences(): Flow<List<Preference>>
    suspend fun getPreferenceCount(): Int
}