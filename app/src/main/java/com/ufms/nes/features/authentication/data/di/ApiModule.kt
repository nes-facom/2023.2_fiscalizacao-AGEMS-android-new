package com.ufms.nes.features.authentication.data.di

import android.util.Log
import com.ufms.nes.features.authentication.data.datastore.LocalService
import com.ufms.nes.features.authentication.data.model.UserResponse
import com.ufms.nes.features.authentication.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideHttpClient(localService: LocalService): HttpClient =
        HttpClient(Android).config {
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("HttpClient", message)
                    }
                }
            }
            install(ContentNegotiation) {
                json()
            }
            install(Auth) {
                bearer {
                    refreshTokens {
                        val token = client.get {
                            markAsRefreshTokenRequest()
                            url("refreshToken")
                            parameter("refreshToken", localService.getRefreshToken())
                        }.body<UserResponse>()
                        BearerTokens(
                            accessToken = token.accessToken.orEmpty(),
                            refreshToken = token.refreshToken.orEmpty()
                        )
                    }
                }
            }
        }

    @Provides
    @Singleton
    fun providePostApi(httpClient: HttpClient, localService: LocalService):ApiService {
        return ApiService(httpClient, localService)
    }
}