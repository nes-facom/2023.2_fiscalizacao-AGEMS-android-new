package com.ufms.nes.features.authentication.data.repository

import com.ufms.nes.features.authentication.data.datastore.LocalService
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val localService: LocalService
) : UserDataRepository {

    override val userLogged = localService.getUserLogged()

    override suspend fun deleteUserPreferences() {
        localService.deleteUserPreferences()
    }
}
