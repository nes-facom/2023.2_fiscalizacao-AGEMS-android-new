package com.ufms.nes.core.data.di

import android.util.Log
import com.ufms.nes.BuildConfig
import com.ufms.nes.core.data.network.ApiService
import com.ufms.nes.features.authentication.data.datastore.LocalService
import com.ufms.nes.features.authentication.data.model.UserResponse
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
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

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
                        val refreshToken = localService.getRefreshToken()
                        val token = client.post("${BuildConfig.BASE_URL}/usuarios/renovar-token") {
                            headers {
                                append(HttpHeaders.Authorization, "Bearer $refreshToken")
                            }
                            markAsRefreshTokenRequest()
                        }.body<UserResponse>()

                        token.accessToken?.let { localService.saveBearerToken(it) }
                        token.refreshToken?.let { localService.saveRefreshToken(it) }

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
    fun providePostApi(httpClient: HttpClient, localService: LocalService): ApiService {
        return ApiService(httpClient, localService)
    }
}
