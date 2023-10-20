package com.ufms.nes.core.data.network

import com.ufms.nes.BuildConfig
import com.ufms.nes.core.data.network.model.AddModelDTO
import com.ufms.nes.core.data.network.model.AddModelResponseDTO
import com.ufms.nes.core.data.network.model.ModelDTO
import com.ufms.nes.features.authentication.data.datastore.LocalService
import com.ufms.nes.features.authentication.data.model.UserDTO
import com.ufms.nes.features.authentication.data.model.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import javax.inject.Inject

class ApiService @Inject constructor(
    private val client: HttpClient,
    private val localService: LocalService
) {

    suspend fun loginUser(user: UserDTO): UserResponse {
        return client.post("${BuildConfig.BASE_URL}/usuarios/autenticar") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun registerUser(user: UserDTO): UserResponse {
        return client.post("${BuildConfig.BASE_URL}/usuarios/cadastro") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun refreshToken(): UserResponse {
        val bearerToken = localService.getBearerToken()
        return client.post("${BuildConfig.BASE_URL}/usuarios/renovar-token") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }.body()
    }

    suspend fun getModels(): List<ModelDTO> {
        val bearerToken = localService.getBearerToken()
        return client.get("${BuildConfig.BASE_URL}/modelo/todos") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }.body()
    }

    suspend fun registerModel(model: AddModelDTO): AddModelResponseDTO {
        val bearerToken = localService.getBearerToken()
        return client.post("${BuildConfig.BASE_URL}/modelo/add") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
            setBody(model)
        }.body()
    }
}
