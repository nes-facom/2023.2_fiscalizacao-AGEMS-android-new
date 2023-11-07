package com.ufms.nes.core.commons.mappers

import com.ufms.nes.core.data.network.model.request.AddModelDTO
import com.ufms.nes.core.data.network.model.request.QuestionDTO
import com.ufms.nes.core.data.network.model.response.QuestionResponseDTO
import com.ufms.nes.core.data.network.model.response.AnswerAlternativeResponseDTO
import com.ufms.nes.core.data.network.model.request.ResponseTypeDTO
import com.ufms.nes.core.database.model.AnswerAlternativeEntity
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.core.database.model.ModelWithQuestionsDataObject
import com.ufms.nes.domain.model.AnswerAlternative
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.model.Question

object Mappers {

    fun ModelWithQuestionsDataObject.toAddModelDTO(alternatives: List<AnswerAlternativeEntity>): AddModelDTO {

        val questions = this.questions.zip(this.modelQuestionEntities).map { pair ->

            val alternativesDTO = alternatives.map { alternative ->
                ResponseTypeDTO(response = alternative.description)
            }

            QuestionDTO(
                question = pair.component1().question,
                objective = pair.component1().isObjective,
                portaria = pair.component1().ordinance,
                responses = ArrayList(alternativesDTO)
            )
        }

        return AddModelDTO(
            name = this.modelEntity.name,
            questions = ArrayList(questions)
        )
    }

    fun QuestionResponseDTO.toQuestion(): Question {

        return Question(
            id = this.id,
            question = this.question,
            isObjective = this.objective,
            portaria = this.portaria,
            responses = this.responses.toAnswerAlternative()
        )
    }

    fun List<QuestionResponseDTO>.toQuestion(): List<Question> {
        return this.map {
            it.toQuestion()
        }
    }

    fun AnswerAlternativeResponseDTO.toAnswerAlternative(): AnswerAlternative {
        return AnswerAlternative(
            id = this.id,
            questionId = this.idQuestion,
            description = this.response,
        )
    }

    fun List<AnswerAlternativeResponseDTO>.toAnswerAlternative(): List<AnswerAlternative> {
        return this.map {
            it.toAnswerAlternative()
        }
    }

    fun ModelEntity.toModel(): Model = Model(
        id = modelId,
        name = name
    )

    fun List<ModelEntity>.toModel(): List<Model> =
        this.map {
            it.toModel()
        }
}
