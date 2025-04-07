package com.j4yesh.tictoetoemultiplayer.Data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.j4yesh.tictoetoemultiplayer.Data.UserPreferences.Companion.KEY_AUTH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class UserPreferences(
    context: Context
) {
    private val KEY_USER = stringPreferencesKey("key_user")

    private val dataStore = context.dataStore
    private val gson = Gson()
    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[KEY_AUTH]
    }


    val user: Flow<User?> = dataStore.data.map { preferences ->
        preferences[KEY_USER]?.let { json ->
            gson.fromJson(json, User::class.java)
        }
    }

    suspend fun saveUser(user: User) {
        val json = gson.toJson(user)
        dataStore.edit { preferences ->
            preferences[KEY_USER] = json
        }
    }

    suspend fun deleteUser() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_USER)
        }
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