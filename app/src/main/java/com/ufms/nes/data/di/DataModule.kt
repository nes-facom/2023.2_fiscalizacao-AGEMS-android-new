package com.ufms.nes.data.di

import com.ufms.nes.data.repository.AuthenticationRepositoryImpl
import com.ufms.nes.data.repository.ConsumeUnitRepositoryImpl
import com.ufms.nes.data.repository.ModelLocalRepositoryImpl
import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.domain.repository.NetworkRepository
import com.ufms.nes.data.repository.NetworkRepositoryImpl
import com.ufms.nes.data.repository.UserDataRepositoryImpl
import com.ufms.nes.domain.repository.AuthenticationRepository
import com.ufms.nes.domain.repository.ConsumeUnitRepository
import com.ufms.nes.domain.repository.UserDataRepository
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
    fun bindsUserModelRepository(
        modelRepository: ModelLocalRepositoryImpl
    ): ModelLocalRepository

    @Singleton
    @Binds
    fun bindsNetworkRepository(
        networkRepository: NetworkRepositoryImpl
    ): NetworkRepository

    @Singleton
    @Binds
    fun bindsConsumeUnitRepository(
        consumeUnitRepository: ConsumeUnitRepositoryImpl
    ): ConsumeUnitRepository

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
