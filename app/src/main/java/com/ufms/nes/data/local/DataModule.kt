package com.ufms.nes.data.local

import com.ufms.nes.data.local.repository.AuthenticationRepositoryImpl
import com.ufms.nes.data.local.repository.ConsumeUnitRepositoryImpl
import com.ufms.nes.data.local.repository.FormRepositoryImpl
import com.ufms.nes.data.local.repository.ModelLocalRepositoryImpl
import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.domain.repository.NetworkRepository
import com.ufms.nes.data.network.repository.NetworkRepositoryImpl
import com.ufms.nes.data.local.repository.UserDataRepositoryImpl
import com.ufms.nes.domain.repository.AuthenticationRepository
import com.ufms.nes.domain.repository.ConsumeUnitRepository
import com.ufms.nes.domain.repository.FormRepository
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
    fun bindsFormRepository(
        formRepository: FormRepositoryImpl
    ): FormRepository

    @Singleton
    @Binds
    fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository
}
