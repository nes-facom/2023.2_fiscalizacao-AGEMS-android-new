package com.ufms.nes.core.commons

import com.ufms.nes.core.data.network.model.ModelDTO
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.core.database.model.QuestionEntity
import com.ufms.nes.core.database.model.QuestionWithResponsesDataObject
import com.ufms.nes.features.template.data.model.Model
import com.ufms.nes.features.template.data.model.Question

fun ModelEntity.toModel(): Model = Model(
    id = modelId,
    name = name
)

fun List<ModelEntity>.toModel(): List<Model> =
    this.map {
        it.toModel()
    }

fun ModelDTO.toModelEntity(): ModelEntity = ModelEntity(
    modelId = id,
    name = name,
    synced = true
)

fun List<ModelDTO>.toModelEntity(): List<ModelEntity> =
    this.map {
        it.toModelEntity()
    }

fun QuestionWithResponsesDataObject.toResponse(): List<String> {
    return responses.zip(questionResponseEntities).mapNotNull { pair ->
        pair.first.response
    }
}

fun Model.toModelEntity(): ModelEntity {
    return ModelEntity(
        modelId = id,
        name = name
    )
}

fun Question.toQuestionEntity(): QuestionEntity {
    return QuestionEntity(
        questionId = id,
        question = question,
        portaria = portaria,
        isObjective = isObjective
    )
}