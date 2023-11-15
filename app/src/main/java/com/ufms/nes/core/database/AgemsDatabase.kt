package com.ufms.nes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ufms.nes.core.database.dao.ConsumeUnitDao
import com.ufms.nes.core.database.dao.ModelDao
import com.ufms.nes.core.database.model.AnswerAlternativeEntity
import com.ufms.nes.core.database.model.ConsumeUnitEntity
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.core.database.model.QuestionEntity
import com.ufms.nes.core.database.model.QuestionModelEntity

@Database(
    entities = [
        ModelEntity::class,
        QuestionModelEntity::class,
        QuestionEntity::class,
        AnswerAlternativeEntity::class,
        ConsumeUnitEntity::class
    ], version = 1, exportSchema = false
)
abstract class AgemsDatabase : RoomDatabase() {

    abstract fun modelDao(): ModelDao
    abstract fun consumeUnitDao(): ConsumeUnitDao
}
