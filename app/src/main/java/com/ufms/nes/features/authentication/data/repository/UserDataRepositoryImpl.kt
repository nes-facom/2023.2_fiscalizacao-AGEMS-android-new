package com.ufms.nes.features.authentication.data.repository

import com.ufms.nes.features.authentication.data.datastore.DataStorePreferences
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val dataStorePreferences: DataStorePreferences
) : UserDataRepository {

    override val userLogged = dataStorePreferences.getUserLogged()

    override suspend fun deleteUserPreferences() {
        dataStorePreferences.deleteUserPreferences()
    }
}
