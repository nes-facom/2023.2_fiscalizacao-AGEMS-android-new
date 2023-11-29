package com.ufms.nes.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ModelWithQuestionsDataObject(
    @Embedded
    val modelEntity: ModelEntity,
    @Relation(
        parentColumn = "model_id",
        entityColumn = "question_id",
        associateBy = Junction(QuestionModelEntity::class)
    )
    val questions: List<QuestionEntity>,
    @Relation(
        parentColumn = "model_id",
        entityColumn = "model_id"
    )
    val modelQuestionEntities: List<QuestionModelEntity>,
)
