package com.ufms.nes.features.template.data.di

import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.features.template.data.repository.ModelLocalRepositoryImpl
import com.ufms.nes.domain.repository.NetworkRepository
import com.ufms.nes.features.template.data.repository.NetworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ModelModule {

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
}
