package com.ufms.nes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ufms.nes.core.database.dao.ModelDao
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.core.database.model.QuestionEntity
import com.ufms.nes.core.database.model.QuestionModelEntity
import com.ufms.nes.core.database.model.QuestionResponseEntity
import com.ufms.nes.core.database.model.ResponseEntity

@Database(
    entities = [
        ModelEntity::class,
        QuestionModelEntity::class,
        QuestionEntity::class,
        ResponseEntity::class,
        QuestionResponseEntity::class
    ], version = 1, exportSchema = false
)
abstract class AgemsDatabase : RoomDatabase() {

    abstract fun modelDao(): ModelDao
}
