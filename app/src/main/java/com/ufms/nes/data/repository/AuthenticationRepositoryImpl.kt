package com.ufms.nes.data.repository

import com.ufms.nes.core.commons.Constants.ERROR_MESSAGE
import com.ufms.nes.core.commons.Resource
import com.ufms.nes.domain.LocalService
import com.ufms.nes.data.network.model.UserDTO
import com.ufms.nes.data.network.model.response.UserResponse
import com.ufms.nes.core.data.network.ApiService
import com.ufms.nes.domain.repository.AuthenticationRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dataStore: LocalService
) : AuthenticationRepository {

    override suspend fun registerUser(user: UserDTO): Resource<UserResponse> {
        return try {
            val result = service.registerUser(user)
            Resource.Success(data = result)
        } catch (ex: Exception) {
            Resource.Error(data = null, error = ERROR_MESSAGE)
        }
    }

    override suspend fun loginUser(user: UserDTO): Resource<UserResponse> {
        return try {
            val result = service.loginUser(user)

            saveInfoInCache(result)

            Resource.Success(data = result)
        } catch (ex: ClientRequestException) {
            val exceptionMessage =
                ex.response.bodyAsText().substringAfter("\"error\":\"").removeSuffix("\"}")

            Resource.Error(data = null, error = exceptionMessage)
        } catch (ex: Throwable) {
            Resource.Error(data = null, error = ERROR_MESSAGE)
        }
    }

    private suspend fun saveInfoInCache(result: UserResponse) {
        result.accessToken?.let {
            dataStore.saveBearerToken(it)
        }
        result.refreshToken?.let {
            dataStore.saveRefreshToken(it)
        }
        dataStore.saveUserLogged(true)
    }

    override suspend fun refreshToken(): Resource<UserResponse> {
        return try {
            val result = service.refreshToken()

            saveInfoInCache(result)

            Resource.Success(data = result)
        } catch (ex: Throwable) {
            Resource.Error(data = null, error = ERROR_MESSAGE)
        }
    }

    companion object {
        const val ACCESS_TOKEN_KEY = "accessToken"
        const val REFRESH_TOKEN_KEY = "refreshToken"
    }
}
