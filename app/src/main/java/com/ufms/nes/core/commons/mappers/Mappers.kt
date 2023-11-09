package com.ufms.nes.core.commons.mappers

import com.ufms.nes.core.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.core.data.network.model.response.AnswerAlternativeResponseDTO
import com.ufms.nes.core.data.network.model.response.QuestionResponseDTO
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.domain.model.AnswerAlternative
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.model.Question

object Mappers {

    fun ModelEntity.toModel(): Model = Model(
        id = modelId,
        name = name
    )

    fun List<ModelEntity>.toModel(): List<Model> =
        this.map {
            it.toModel()
        }

    fun List<AddModelResponseDTO>.toModelDomain(): List<Model> {
        return this.map { modelResponse ->
            Model(
                id = modelResponse.id,
                name = modelResponse.name.orEmpty(),
                questions = modelResponse.questions.toQuestion()
            )
        }
    }

    private fun QuestionResponseDTO.toQuestion(): Question {

        return Question(
            id = this.id,
            question = this.question,
            isObjective = this.objective,
            portaria = this.portaria,
            responses = this.responses.toAnswerAlternative()
        )
    }

    private fun List<QuestionResponseDTO>.toQuestion(): List<Question> {
        return this.map {
            it.toQuestion()
        }
    }

    private fun AnswerAlternativeResponseDTO.toAnswerAlternative(): AnswerAlternative {
        return AnswerAlternative(
            id = this.id,
            questionId = this.idQuestion,
            description = this.response,
        )
    }

    private fun List<AnswerAlternativeResponseDTO>.toAnswerAlternative(): List<AnswerAlternative> {
        return this.map {
            it.toAnswerAlternative()
        }
    }
}