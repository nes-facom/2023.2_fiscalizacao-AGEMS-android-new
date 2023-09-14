package com.ufms.nes.features.authentication.data.di

import com.ufms.nes.features.authentication.data.repository.AuthenticationRepository
import com.ufms.nes.features.authentication.data.repository.AuthenticationRepositoryImpl
import com.ufms.nes.features.authentication.data.repository.UserDataRepository
import com.ufms.nes.features.authentication.data.repository.UserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsAuthenticationRepository(
        authenticationRepository: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Singleton
    @Binds
    fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository
}
