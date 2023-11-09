package com.ufms.nes.core.database.di

import android.content.Context
import androidx.room.Room
import com.ufms.nes.core.database.AgemsDatabase
import com.ufms.nes.core.database.dao.ModelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideModelDao(agemsDatabase: AgemsDatabase): ModelDao {
        return agemsDatabase.modelDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AgemsDatabase {
        return Room.databaseBuilder(
            appContext,
            AgemsDatabase::class.java,
            "agems_db"
        ).build()
    }
}
