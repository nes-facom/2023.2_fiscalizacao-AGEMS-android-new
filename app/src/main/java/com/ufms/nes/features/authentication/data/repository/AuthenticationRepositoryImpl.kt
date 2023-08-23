package com.ufms.nes.features.authentication.data.repository

import com.ufms.nes.core.commons.Resource
import com.ufms.nes.features.authentication.data.model.User
import com.ufms.nes.features.authentication.data.model.UserResponse
import com.ufms.nes.features.authentication.data.service.ApiService
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val service: ApiService
) : AuthenticationRepository {

    override suspend fun registerUser(user: User): Resource<UserResponse> {
        return try {
            val result = service.registerUser(user)
            Resource.Success(data = result)
        } catch (ex: Exception) {
            Resource.Error(data = null, error = "Desculpe, ocorreu algum erro")
        }
    }

    override suspend fun loginUser(user: User): Resource<UserResponse> {
        return try {
            val result = service.loginUser(user)
            Resource.Success(data = result)
        } catch (ex: ClientRequestException) {
            val exceptionMessage =
                ex.response.bodyAsText().substringAfter("\"error\":\"").removeSuffix("\"}")

            Resource.Error(data = null, error = exceptionMessage)
        } catch (ex: Throwable) {
            Resource.Error(data = null, error = "Desculpe, ocorreu algum erro")
        }
    }
}
