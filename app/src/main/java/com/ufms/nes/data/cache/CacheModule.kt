package com.ufms.nes.data.cache

import android.content.Context
import com.ufms.nes.domain.LocalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext app: Context): LocalService =
        LocalServiceImpl(app)
}
