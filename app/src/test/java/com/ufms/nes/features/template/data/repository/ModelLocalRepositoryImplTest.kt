package com.ufms.nes.features.template.data.repository

import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.core.database.dao.ModelDao
import com.ufms.nes.core.database.model.AnswerAlternativeEntity
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.core.database.model.ModelWithQuestionsDataObject
import com.ufms.nes.core.database.model.QuestionEntity
import com.ufms.nes.core.database.model.QuestionModelEntity
import com.ufms.nes.domain.repository.ModelLocalRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.UUID

fun createTestModelEntity(): ModelEntity {
    return ModelEntity(
        modelId = UUID.randomUUID(),
        name = "Test Model",
        syncState = SyncState.SYNCED // or SyncState.EDITED, depending on your testing scenario
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


class ModelLocalRepositoryImplTest {

    @MockK
    private lateinit var modelDao: ModelDao
    private lateinit var modelLocalRepository: ModelLocalRepository

    @Before
    fun setup() {
        modelDao = mockk()
        modelLocalRepository = ModelLocalRepositoryImpl(modelDao)
    }

    @Test
    fun getModelsList() = runBlocking {
        val testModels = listOf(createTestModelEntity())
        val flow = MutableStateFlow(testModels)
//
        coEvery { modelDao.observeAllModels() } returns flow
//
        val result = modelLocalRepository.getModelsList()
//
        assertEquals(result.first()[0].name, "Test Model")
//        result.collect { models ->
//            assertEquals(testModels.map { it.toModel() }, models)
//        }
    }

//    @Test
//    fun insertModel() {
//    }
//
//    @Test
//    fun getAllUnSyncedModel() = runBlocking {
//
//    }
//
    @Test
    fun getModelById() = runBlocking {
        val modelId = UUID.randomUUID()
        val mockModel = mockModelWithQuestionsDataObject()
        val mockAnswerAlternatives = createMockAnswerAlternatives()

        coEvery { modelDao.getModelWithQuestions(modelId) } returns mockModel
        coEvery { modelDao.getAnswerAlternativeByQuestionId(modelId) } returns mockAnswerAlternatives

        val returnModel = modelLocalRepository.getModelById(modelId)

        assertEquals(mockModel.questions, returnModel?.questions)
    }
//
//    @Test
//    fun getAnswerAlternativeByQuestionId() {
//    }
//
//    @Test
//    fun clearSyncedData() {
//    }
//
//    @Test
//    fun updateModel() {
//    }
//
//    @Test
//    fun updateQuestionModel() {
//    }
//
//    @Test
//    fun testUpdateQuestionModel() {
//    }
//
//    @Test
//    fun updateQuestion() {
//    }
//
//    @Test
//    fun updateAnswerAlternative() {
//    }
}