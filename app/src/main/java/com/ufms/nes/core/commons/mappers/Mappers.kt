package com.ufms.nes.core.commons.mappers

import com.ufms.nes.data.network.model.request.AddConsumeUnitDTO
import com.ufms.nes.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.data.network.model.response.AnswerAlternativeResponseDTO
import com.ufms.nes.data.network.model.response.ConsumeUnitItemResponseDTO
import com.ufms.nes.data.network.model.response.QuestionResponseDTO
import com.ufms.nes.data.local.model.ConsumeUnitEntity
import com.ufms.nes.data.local.model.ModelEntity
import com.ufms.nes.data.local.model.ResponseEntity
import com.ufms.nes.data.network.model.request.ResponseDTO
import com.ufms.nes.data.network.model.response.FormResponseDto
import com.ufms.nes.domain.model.AnswerAlternative
import com.ufms.nes.domain.model.ConsumeUnit
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.model.Question
import com.ufms.nes.features.form.util.Form

object Mappers {

    fun ModelEntity.toModel(): Model = Model(
        id = modelId,
        name = name
    )

    fun ConsumeUnitEntity.toConsumeUnit(): ConsumeUnit = ConsumeUnit(
        id = unitId,
        name = name,
        address = address,
        type = type
    )

    fun ConsumeUnitEntity.toConsumeUnitDTO(): AddConsumeUnitDTO = AddConsumeUnitDTO(
        idLocal = unitId,
        name = name,
        address = address,
        type = type
    )

    fun ConsumeUnitItemResponseDTO.toConsumeUnitDTO(): ConsumeUnit = ConsumeUnit(
        id = id,
        name = name,
        address = address,
        type = type
    )

    fun List<ResponseEntity>.toResponseDTO(): List<ResponseDTO> =
        this.map {
            it.toResponseDTO()
        }

    fun ResponseEntity.toResponseDTO(): ResponseDTO = ResponseDTO(
        idLocal = responseId,
        idQuestion = questionId,
        response = response,
    )

    fun List<ModelEntity>.toModel(): List<Model> =
        this.map {
            it.toModel()
        }

    fun List<ConsumeUnitEntity>.toConsumeUnitDomain(): List<ConsumeUnit> =
        this.map {
            it.toConsumeUnit()
        }

    fun List<ConsumeUnitItemResponseDTO>.toConsumeUnit(): List<ConsumeUnit> =
        this.map {
            it.toConsumeUnitDTO()
        }

    fun List<AddModelResponseDTO>.toModelDomain(): List<Model> {
        return this.map { modelResponse ->
            Model(
                id = modelResponse.id,
                idLocal = modelResponse.idLocal,
                name = modelResponse.name.orEmpty(),
                questions = modelResponse.questions.toQuestion()
            )
        }
    }

    private fun QuestionResponseDTO.toQuestion(): Question {

        return Question(
            id = this.id,
            idLocal = this.idLocal,
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

    fun FormResponseDto.mapFromEntity() = Form(
        id = this.id,
        unit = this.unit,
        creationDate = this.dateCreated,
        user = this.userCreated
    )

    fun List<FormResponseDto>.mapFromListModel(): List<Form> {
        return this.map {
            it.mapFromEntity()
        }
    }
}
