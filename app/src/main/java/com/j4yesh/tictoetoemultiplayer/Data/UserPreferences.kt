package com.j4yesh.tictoetoemultiplayer.Data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class UserPreferences(
    context: Context
) {
    private val dataStore = context.dataStore

    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[KEY_AUTH]
    }

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    suspend fun deleteAuthToken() {
        dataStore.edit { preferences ->
//            preferences.remove(KEY_AUTH)
            preferences.clear()
        }
    }

    companion object {
        private val KEY_AUTH = stringPreferencesKey("key_auth")
    }
}