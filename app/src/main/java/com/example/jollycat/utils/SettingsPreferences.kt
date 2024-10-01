package com.example.jollycat.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SettingsPreferences.PREFERENCES_NAME)

class SettingsPreferences(private val dataStore: DataStore<Preferences>) {
    fun getUserID() =
        dataStore.data.map {
            it[USER_ID_PREFERENCES] ?: -1
        }

    fun getUsername() =
        dataStore.data.map {
            it[USERNAME_PREFERENCES] ?: PREFERENCE_DEFAULT_VALUE
        }

    fun getPhoneNumber() =
        dataStore.data.map {
            it[PHONENUMBER_PREFERENCES] ?: PREFERENCE_DEFAULT_VALUE
        }

    suspend fun saveSession(
        userID: Int,
        username: String,
        password: String,
        phoneNumber: String
    ) {
        dataStore.edit { prefs ->
            prefs[USER_ID_PREFERENCES] = userID
            prefs[USERNAME_PREFERENCES] = username
            prefs[PHONENUMBER_PREFERENCES] = phoneNumber
            prefs[PASSWORD_PREFERENCES] = password
        }
    }

    suspend fun clearPreferences() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    companion object {
        private val USER_ID_PREFERENCES = intPreferencesKey("user_is_preferences")
        private val USERNAME_PREFERENCES = stringPreferencesKey("username_preferences")
        private val PASSWORD_PREFERENCES = stringPreferencesKey("password_preferences")
        private val PHONENUMBER_PREFERENCES = stringPreferencesKey("phonenumber_preferences")

        const val PREFERENCES_NAME = "settings_preferences"
        const val PREFERENCE_DEFAULT_VALUE = "preference_default_value"
    }
}