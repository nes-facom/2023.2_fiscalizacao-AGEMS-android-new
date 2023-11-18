package com.ufms.nes.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FormWithQuestionsDataObject(
    @Embedded
    val formEntity: FormEntity,
    @Relation(
        parentColumn = "form_id",
        entityColumn = "question_id",
        associateBy = Junction(QuestionFormEntity::class)
    )
    val questions: List<QuestionEntity>,
    @Relation(
        parentColumn = "form_id",
        entityColumn = "form_id"
    )
    val modelQuestionEntities: List<QuestionFormEntity>,
)
