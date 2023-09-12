package com.ufms.nes.features.models.data.di

import com.ufms.nes.features.models.data.repository.ModelRepository
import com.ufms.nes.features.models.data.repository.ModelRepositoryImpl
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
        modelRepository: ModelRepositoryImpl
    ): ModelRepository
}
