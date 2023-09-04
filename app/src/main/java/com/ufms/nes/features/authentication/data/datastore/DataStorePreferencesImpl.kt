package com.ufms.nes.features.authentication.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ufms.nes.BuildConfig
import com.ufms.nes.features.authentication.data.repository.AuthenticationRepositoryImpl.Companion.ACCESS_TOKEN_KEY
import com.ufms.nes.features.authentication.data.repository.AuthenticationRepositoryImpl.Companion.REFRESH_TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BuildConfig.DS_NAME)

class DataStorePreferencesImpl @Inject constructor(
    private val context: Context
) : DataStorePreferences {

    override suspend fun saveStringValue(key: String, value: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    override fun getUserLogged(): Flow<Boolean> {
        return context.dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[booleanPreferencesKey(USER_KEY)] ?: false
        }
    }

    override suspend fun deleteUserPreferences() {
        context.dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(USER_KEY))
            preferences.remove(stringPreferencesKey(ACCESS_TOKEN_KEY))
            preferences.remove(stringPreferencesKey(REFRESH_TOKEN_KEY))
        }
    }

    override suspend fun saveUserLogged(logged: Boolean) {
        val preferenceKey = booleanPreferencesKey(USER_KEY)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = logged
        }
    }

    override suspend fun getStringValue(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val USER_KEY = "user_logged"
    }
}
