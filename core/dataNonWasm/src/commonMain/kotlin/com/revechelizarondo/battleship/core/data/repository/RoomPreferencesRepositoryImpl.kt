package com.revechelizarondo.battleship.core.data.repository

import com.revechelizarondo.battleship.core.data.source.BattleshipDatabase
import com.revechelizarondo.battleship.core.domain.models.Preference
import com.revechelizarondo.battleship.core.domain.models.PreferenceKey
import com.revechelizarondo.battleship.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class RoomPreferencesRepositoryImpl(private val database: BattleshipDatabase) : PreferencesRepository {
    override suspend fun upsertPreference(key: PreferenceKey, value: String?) {
        database.preferencesDao().savePreference(Preference(key.name, value))
    }

    override suspend fun getPreference(key: PreferenceKey): Preference? {
        return database.preferencesDao().getPreference(key.name)
    }

    override fun getPreferences(): Flow<List<Preference>> {
        return database.preferencesDao().getPreferences()
    }

    override suspend fun getPreferenceCount(): Int {
        return database.preferencesDao().getPreferenceCount()
    }
}