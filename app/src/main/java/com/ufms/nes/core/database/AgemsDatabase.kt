package com.ufms.nes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ufms.nes.core.database.dao.ConsumeUnitDao
import com.ufms.nes.core.database.dao.FormDao
import com.ufms.nes.core.database.dao.ModelDao
import com.ufms.nes.data.local.model.AnswerAlternativeEntity
import com.ufms.nes.data.local.model.ConsumeUnitEntity
import com.ufms.nes.data.local.model.FormEntity
import com.ufms.nes.data.local.model.ModelEntity
import com.ufms.nes.data.local.model.QuestionEntity
import com.ufms.nes.data.local.model.QuestionFormEntity
import com.ufms.nes.data.local.model.QuestionModelEntity
import com.ufms.nes.data.local.model.ResponseEntity

@Database(
    entities = [
        ModelEntity::class,
        QuestionModelEntity::class,
        QuestionEntity::class,
        AnswerAlternativeEntity::class,
        ConsumeUnitEntity::class,
        FormEntity::class,
        ResponseEntity::class,
        QuestionFormEntity::class
    ], version = 1, exportSchema = false
)
abstract class AgemsDatabase : RoomDatabase() {

    abstract fun modelDao(): ModelDao
    abstract fun consumeUnitDao(): ConsumeUnitDao
    abstract fun formDao(): FormDao
}
