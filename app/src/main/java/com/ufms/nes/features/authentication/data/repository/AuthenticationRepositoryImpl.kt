package com.ufms.nes.features.authentication.data.repository

import com.ufms.nes.core.commons.Constants.ERROR_MESSAGE
import com.ufms.nes.core.commons.Resource
import com.ufms.nes.core.utils.getHttpExceptionMessage
import com.ufms.nes.features.authentication.data.datastore.DataStorePreferences
import com.ufms.nes.features.authentication.data.model.User
import com.ufms.nes.features.authentication.data.model.UserResponse
import com.ufms.nes.features.authentication.data.service.ApiService
import io.ktor.client.plugins.ClientRequestException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dataStore: DataStorePreferences
) : AuthenticationRepository {

    override suspend fun registerUser(user: User): Resource<UserResponse> {
        return try {
            val result = service.registerUser(user)

            saveInfoInCache(result)

            Resource.Success(data = result)
        } catch (ex: ClientRequestException) {
            Resource.Error(data = null, error = ex.response.getHttpExceptionMessage())
        } catch (ex: Throwable) {
            Resource.Error(data = null, error = ERROR_MESSAGE)
        }
    }

    override suspend fun loginUser(user: User): Resource<UserResponse> {
        return try {
            val result = service.loginUser(user)

            saveInfoInCache(result)

            Resource.Success(data = result)
        } catch (ex: ClientRequestException) {
            Resource.Error(data = null, error = ex.response.getHttpExceptionMessage())
        } catch (ex: Throwable) {
            Resource.Error(data = null, error = ERROR_MESSAGE)
        }
    }

    private suspend fun saveInfoInCache(result: UserResponse) {
        result.accessToken?.let {
            dataStore.saveStringValue(ACCESS_TOKEN_KEY, it)
        }
        result.refreshToken?.let {
            dataStore.saveStringValue(REFRESH_TOKEN_KEY, it)
        }
        dataStore.saveUserLogged(true)
    }

    companion object {
        const val ACCESS_TOKEN_KEY = "accessToken"
        const val REFRESH_TOKEN_KEY = "refreshToken"
    }
}
