package com.ufms.nes.data.local.repository

import com.ufms.nes.domain.repository.UserDataRepository
import com.ufms.nes.domain.LocalService
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val localService: LocalService
) : UserDataRepository {

    override val userLogged = localService.getUserLogged()

    override suspend fun deleteUserPreferences() {
        localService.deleteUserPreferences()
    }
}
