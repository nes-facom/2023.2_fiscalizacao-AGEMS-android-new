package com.ufms.nes.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ModelWithQuestionsDataObject(
    @Embedded
    val modelEntity: ModelEntity,
    @Relation(
        parentColumn = "modelId",
        entityColumn = "questionId",
        associateBy = Junction(QuestionModelEntity::class)
    )
    val questions: List<QuestionEntity>,
    @Relation(
        parentColumn = "modelId",
        entityColumn = "modelId"
    )
    val modelQuestionEntities: List<QuestionModelEntity>,
)
