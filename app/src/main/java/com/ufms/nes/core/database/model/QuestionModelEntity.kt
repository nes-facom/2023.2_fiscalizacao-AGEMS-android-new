package com.ufms.nes.core.database.model

import androidx.room.Entity

@Entity(tableName = "question_model_entity", primaryKeys = ["questionId", "modelId"])
data class QuestionModelEntity(
    val questionId: Long,
    val modelId: Long,
    val synced: Boolean = false
)
