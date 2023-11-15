package com.ufms.nes.core.data.di

import android.content.Context
import com.ufms.nes.domain.LocalService
import com.ufms.nes.data.cache.LocalServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext app: Context): LocalService =
        LocalServiceImpl(app)
}
