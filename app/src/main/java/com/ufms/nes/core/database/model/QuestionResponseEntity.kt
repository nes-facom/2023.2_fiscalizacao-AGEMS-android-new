package com.ufms.nes.core.database.model

import androidx.room.Entity

@Entity(tableName = "question_response_entity", primaryKeys = ["questionId", "responseId"])
data class QuestionResponseEntity(
    val questionId: Long,
    val responseId: Long,
    val synced: Boolean = false
)
