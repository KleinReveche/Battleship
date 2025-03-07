package com.revechelizarondo.battleship.core.data.source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.revechelizarondo.battleship.core.domain.models.Preference
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferencesDao {
    @Query("SELECT * FROM preferences")
    fun getPreferences(): Flow<List<Preference>>

    @Query("SELECT * FROM preferences WHERE `key` = :key")
    suspend fun getPreference(key: String): Preference?

    @Upsert
    suspend fun savePreference(preference: Preference)

    @Delete
    suspend fun deletePreference(preference: Preference)

    @Query("SELECT COUNT(*) FROM preferences")
    suspend fun getPreferenceCount(): Int
}