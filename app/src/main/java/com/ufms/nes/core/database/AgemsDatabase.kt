package com.ufms.nes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ufms.nes.core.database.dao.ModelDao
import com.ufms.nes.core.database.model.ModelEntity

@Database(entities = [ModelEntity::class], version = 1, exportSchema = false)
abstract class AgemsDatabase : RoomDatabase() {

    abstract fun modelDao(): ModelDao
}
