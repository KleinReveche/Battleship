package com.revechelizarondo.battleship.core.data.repository

import com.revechelizarondo.battleship.core.domain.models.Preference
import com.revechelizarondo.battleship.core.domain.models.PreferenceKey
import com.revechelizarondo.battleship.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.w3c.dom.Storage

class LocalStoragePreferencesRepositoryImpl(
    private val localStorage: Storage
) : PreferencesRepository {
    private val delay: Long = 1000L

    override suspend fun upsertPreference(key: PreferenceKey, value: String?) {
        localStorage.setItem(key.name, value ?: "null")
    }

    override suspend fun getPreference(key: PreferenceKey): Preference? {
        var value: String? = localStorage.getItem(key.name) ?: return null

        if (value == "null")
            value = null

        return Preference(key.name, value)
    }

    override fun getPreferences(): Flow<List<Preference>> = flow {
        while (true) {
            val preferences = PreferenceKey.entries.mapNotNull { key ->
                var value: String? = localStorage.getItem(key.name) ?: return@mapNotNull null

                if (value == "null")
                    value = null

                Preference(key.name, value)
            }

            emit(preferences)
            delay(delay)
        }
    }

    override suspend fun getPreferenceCount(): Int {
        return PreferenceKey.entries.mapNotNull { key ->
            var value: String? = localStorage.getItem(key.name) ?: return@mapNotNull null

            if (value == "null")
                value = null

            Preference(key.name, value)
        }.size
    }
}