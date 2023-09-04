package com.ufms.nes.features.authentication.data.repository

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userLogged: Flow<Boolean>

    suspend fun deleteUserPreferences()
}
