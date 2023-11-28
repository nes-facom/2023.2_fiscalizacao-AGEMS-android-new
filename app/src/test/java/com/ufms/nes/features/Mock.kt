package com.ufms.nes.features

import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.core.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.core.data.network.model.response.AnswerAlternativeResponseDTO
import com.ufms.nes.core.data.network.model.response.QuestionResponseDTO
import com.ufms.nes.core.database.model.AnswerAlternativeEntity
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.core.database.model.ModelWithQuestionsDataObject
import com.ufms.nes.core.database.model.QuestionEntity
import com.ufms.nes.core.database.model.QuestionModelEntity
import java.util.UUID

object Mocks {
    const val loginSuccessfulAuthToken = "loginAuthToken"
    const val loginSuccessfulRefreshToken = "loginRefreshToken"
    const val registerSuccessfulAuthToken = "registerAuthToken"
    const val registerSuccessfulRefreshToken = "registerRefreshToken"
}

fun createTestModelEntity(): ModelEntity {
    return ModelEntity(
        modelId = UUID.randomUUID(),
        name = "Test Model",
        syncState = SyncState.SYNCED
    )
}

fun mockModelWithQuestionsDataObject(): ModelWithQuestionsDataObject {
    val modelId = UUID.randomUUID()
    val questionId1 = UUID.randomUUID()
    val questionId2 = UUID.randomUUID()

    val modelEntity = ModelEntity(
        modelId = modelId,
        name = "Mock Model",
        syncState = SyncState.SYNCED
    )

    val questionEntity1 = QuestionEntity(
        questionId = questionId1,
        question = "Mock Question 1",
        isObjective = true,
        ordinance = "Mock Ordinance 1",
        syncState = SyncState.SYNCED
    )

    val questionEntity2 = QuestionEntity(
        questionId = questionId2,
        question = "Mock Question 2",
        isObjective = false,
        ordinance = "Mock Ordinance 2",
        syncState = SyncState.EDITED
    )

    val questionModelEntity1 = QuestionModelEntity(
        questionId = questionId1,
        modelId = modelId,
        syncState = SyncState.SYNCED
    )

    val questionModelEntity2 = QuestionModelEntity(
        questionId = questionId2,
        modelId = modelId,
        syncState = SyncState.EDITED
    )

    return ModelWithQuestionsDataObject(
        modelEntity = modelEntity,
        questions = listOf(questionEntity1, questionEntity2),
        modelQuestionEntities = listOf(questionModelEntity1, questionModelEntity2)
    )
}

fun createMockAnswerAlternatives(): List<AnswerAlternativeEntity> {
    val alternative1 = AnswerAlternativeEntity(
        alternativeId = UUID.randomUUID(),
        questionId = UUID.randomUUID(),
        description = "Option A",
        syncState = SyncState.SYNCED
    )
    val alternative2 = AnswerAlternativeEntity(
        alternativeId = UUID.randomUUID(),
        questionId = UUID.randomUUID(),
        description = "Option B",
        syncState = SyncState.SYNCED
    )

    return listOf(alternative1, alternative2)
}

fun createMockAddModelResponseDTO(): AddModelResponseDTO {
    val modelId = UUID.randomUUID()

    val questionResponseDTOs = ArrayList(listOf(
        createMockQuestionResponseDTO(),
        createMockQuestionResponseDTO(),
    ))

    return AddModelResponseDTO(
        id = modelId,
        name = "Mock Model",
        questions = questionResponseDTOs
    )
}

fun createMockQuestionResponseDTO(): QuestionResponseDTO {
    val questionId = UUID.randomUUID()

    val answerAlternativeResponseDTOs = ArrayList(listOf(
        createMockAnswerAlternativeResponseDTO(),
        createMockAnswerAlternativeResponseDTO(),
    ))

    return QuestionResponseDTO(
        id = questionId,
        question = "Mock Question",
        objective = true,
        portaria = "Mock Portaria",
        responses = answerAlternativeResponseDTOs
    )
}

fun createMockAnswerAlternativeResponseDTO(): AnswerAlternativeResponseDTO {
    val answerAlternativeId = UUID.randomUUID()
    val questionId = UUID.randomUUID()

    return AnswerAlternativeResponseDTO(
        id = answerAlternativeId,
        response = "Mock Response",
        idQuestion = questionId
    )
}
