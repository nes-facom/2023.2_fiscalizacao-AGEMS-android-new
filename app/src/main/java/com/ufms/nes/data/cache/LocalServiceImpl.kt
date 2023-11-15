package com.ufms.nes.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ufms.nes.BuildConfig
import com.ufms.nes.data.repository.AuthenticationRepositoryImpl.Companion.ACCESS_TOKEN_KEY
import com.ufms.nes.data.repository.AuthenticationRepositoryImpl.Companion.REFRESH_TOKEN_KEY
import com.ufms.nes.domain.LocalService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BuildConfig.DS_NAME)

class LocalServiceImpl @Inject constructor(
    private val context: Context
) : LocalService {

    override fun getUserLogged(): Flow<Boolean> {
        return context.dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[userKey] ?: false
        }
    }

    override suspend fun deleteUserPreferences() {
        context.dataStore.edit { preferences ->
            preferences.remove(userKey)
            preferences.remove(stringPreferencesKey(ACCESS_TOKEN_KEY))
            preferences.remove(stringPreferencesKey(REFRESH_TOKEN_KEY))
        }
    }

    override suspend fun saveUserLogged(logged: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[userKey] = logged
        }
    }

    override suspend fun saveBearerToken(bearerToken: String) {
        context.dataStore.edit {
            it[bearerTokenKey] = bearerToken
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit {
            it[refreshTokenKey] = refreshToken
        }
    }

    override suspend fun getBearerToken(): String? {
        return context.dataStore.data.firstOrNull()?.get(bearerTokenKey)
    }

    override suspend fun getRefreshToken(): String? {
        return context.dataStore.data.firstOrNull()?.get(refreshTokenKey)
    }

    companion object {
        private val userKey = booleanPreferencesKey("user_logged")
        private val bearerTokenKey = stringPreferencesKey("bearer_token")
        private val refreshTokenKey = stringPreferencesKey("refresh_token")
    }
}
