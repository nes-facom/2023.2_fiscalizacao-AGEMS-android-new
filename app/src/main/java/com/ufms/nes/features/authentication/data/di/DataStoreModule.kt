package com.ufms.nes.features.authentication.data.di

import android.content.Context
import com.ufms.nes.features.authentication.data.datastore.DataStorePreferences
import com.ufms.nes.features.authentication.data.datastore.DataStorePreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext app: Context): DataStorePreferences =
        DataStorePreferencesImpl(app)
}
