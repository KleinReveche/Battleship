package com.revechelizarondo.battleship.data.repository

import com.revechelizarondo.battleship.domain.models.Preference
import com.revechelizarondo.battleship.domain.models.PreferenceKey
import com.revechelizarondo.battleship.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.w3c.dom.Storage

class BrowserLocalRepositoryImpl(
    private val localStorage: Storage
) : LocalRepository {
    private val delay: Long = 1000L

    override suspend fun upsertPreference(key: PreferenceKey, value: String?) {
        localStorage.setItem(key.name, value ?: "null")
    }

    override suspend fun getPreference(key: PreferenceKey): Preference? {
        val value = localStorage.getItem(key.name)
        return if (value != null) {
            val isNull = value == "null"
            Preference(key.name, if (isNull) null else value)
        } else {
            null
        }
    }

    override fun getPreferences(): Flow<List<Preference>> = flow {
        while (true) {
            val preferences = PreferenceKey.entries.mapNotNull { getPreference(it) }
            emit(preferences)
            kotlinx.coroutines.delay(delay)
        }
    }

    override suspend fun getPreferenceCount(): Int {
        return PreferenceKey.entries.mapNotNull { getPreference(it) }.size
    }
}