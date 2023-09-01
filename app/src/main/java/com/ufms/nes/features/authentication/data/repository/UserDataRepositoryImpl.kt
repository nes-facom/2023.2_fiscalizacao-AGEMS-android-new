package com.ufms.nes.features.authentication.data.repository

import com.ufms.nes.features.authentication.data.datastore.DataStorePreferences
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    dataStorePreferences: DataStorePreferences
) : UserDataRepository {

    override val userLogged = dataStorePreferences.getUserLogged()
}
