package com.revechelizarondo.battleship.data.repository

import com.revechelizarondo.battleship.data.source.BattleshipDatabase
import com.revechelizarondo.battleship.domain.models.Preference
import com.revechelizarondo.battleship.domain.models.PreferenceKey
import com.revechelizarondo.battleship.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class RoomLocalRepositoryImpl(private val database: BattleshipDatabase) : LocalRepository {
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