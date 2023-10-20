package com.ufms.nes.features.template.data.mapper

import com.ufms.nes.core.database.model.QuestionEntity
import com.ufms.nes.features.template.data.model.Question

fun Question.toQuestionEntity(): QuestionEntity {
    return QuestionEntity(
        questionId = id,
        question = question,
        portaria = portaria,
        isObjective = isObjective
    )
}