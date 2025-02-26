package com.revechelizarondo.battleship.domain.repository

import com.revechelizarondo.battleship.domain.models.Preference
import com.revechelizarondo.battleship.domain.models.PreferenceKey
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun upsertPreference(key: PreferenceKey, value: String?)
    suspend fun getPreference(key: PreferenceKey): Preference?
    fun getPreferences(): Flow<List<Preference>>
    suspend fun getPreferenceCount(): Int
}