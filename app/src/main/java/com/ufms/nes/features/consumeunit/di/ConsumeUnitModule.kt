package com.ufms.nes.features.consumeunit.di

import com.ufms.nes.features.consumeunit.data.ConsumeUnitRepository
import com.ufms.nes.features.consumeunit.data.ConsumeUnitRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ConsumeUnitModule {

    @Singleton
    @Binds
    fun bindsConsumeUnitRepository(
        consumeUnitRepository: ConsumeUnitRepositoryImpl
    ): ConsumeUnitRepository
}
