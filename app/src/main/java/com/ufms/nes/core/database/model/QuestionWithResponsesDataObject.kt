package com.ufms.nes.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class QuestionWithResponsesDataObject(
    @Embedded
    val questionEntity: QuestionEntity,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "responseId",
        associateBy = Junction(QuestionResponseEntity::class)
    )
    val responses: List<ResponseEntity>,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val questionResponseEntities: List<QuestionResponseEntity>
)
