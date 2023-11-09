package com.ufms.nes.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_entity")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val questionId: Long? = null,
    var question: String? = null,
    var isObjective: Boolean? = null,
    var portaria: String? = null,
    val synced: Boolean = false
)
