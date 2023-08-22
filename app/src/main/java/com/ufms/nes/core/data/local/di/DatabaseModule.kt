package com.ufms.nes.core.data.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.ufms.nes.core.data.local.AppDatabase
import com.ufms.nes.core.data.local.AgemsTypeDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideAgemsTypeDao(appDatabase: AppDatabase): AgemsTypeDao {
        return appDatabase.agemsTypeDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "AgemsType"
        ).build()
    }
}
