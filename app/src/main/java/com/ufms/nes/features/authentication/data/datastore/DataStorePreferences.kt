package com.ufms.nes.features.authentication.data.datastore

import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {

    fun getUserLogged(): Flow<Boolean>

    suspend fun deleteUserPreferences()

    suspend fun saveUserLogged(logged: Boolean)

    suspend fun saveStringValue(key: String, value: String)
    
    suspend fun getStringValue(key: String): String?
}
