package com.ufms.nes.features.authentication.data.service

import com.ufms.nes.features.authentication.data.model.UserResponse
import com.ufms.nes.features.authentication.data.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import javax.inject.Inject

private const val BASE_URL = "http://54.210.245.243:8080"

class ApiService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun loginUser(user: User): UserResponse {
        return client.post("$BASE_URL/usuarios/autenticar") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun registerUser(user: User): UserResponse {
        return client.post("$BASE_URL/usuarios/cadastro") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(user)
        }.body()
    }
}