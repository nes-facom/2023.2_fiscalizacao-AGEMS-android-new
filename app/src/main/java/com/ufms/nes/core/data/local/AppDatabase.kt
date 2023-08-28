

package com.ufms.nes.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AgemsType::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun agemsTypeDao(): AgemsTypeDao
}
