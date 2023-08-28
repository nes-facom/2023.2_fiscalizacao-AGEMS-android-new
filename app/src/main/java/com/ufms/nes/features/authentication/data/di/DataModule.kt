package com.ufms.nes.features.authentication.data.di

import com.ufms.nes.features.authentication.data.repository.AuthenticationRepository
import com.ufms.nes.features.authentication.data.repository.AuthenticationRepositoryImpl
import com.ufms.nes.features.authentication.data.service.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsAuthenticationRepository(
        authenticationRepository: AuthenticationRepositoryImpl
    ): AuthenticationRepository
}

//class FakeAuthenticationRepository @Inject constructor() : AuthenticationRepository {
//    override suspend fun registerUser(user: User): Resource<UserResponse> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun loginUser(user: User): Resource<UserResponse> {
//        TODO("Not yet implemented")
//    }
//
//}
