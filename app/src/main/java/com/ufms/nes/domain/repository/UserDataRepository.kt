package com.ufms.nes.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userLogged: Flow<Boolean>

    suspend fun deleteUserPreferences()
}
