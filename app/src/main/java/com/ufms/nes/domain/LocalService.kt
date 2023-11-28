package com.ufms.nes.domain

import kotlinx.coroutines.flow.Flow

interface LocalService {

    fun getUserLogged(): Flow<Boolean>

    suspend fun deleteUserPreferences()

    suspend fun saveUserLogged(logged: Boolean)

    suspend fun saveBearerToken(bearerToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun getBearerToken(): String?

    suspend fun getRefreshToken(): String?
}
