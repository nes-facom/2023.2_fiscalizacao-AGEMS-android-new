package com.ufms.nes.features.template.data.repository

import com.ufms.nes.core.database.dao.ModelDao
import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.features.createMockAnswerAlternatives
import com.ufms.nes.features.createTestModelEntity
import com.ufms.nes.features.mockModelWithQuestionsDataObject
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

        coEvery { modelDao.observeAllModels() } returns flow

        val result = modelLocalRepository.getModelsList()

        assertEquals(result.first()[0].name, "Test Model")
//        result.collect { models ->
//            assertEquals(testModels.map { it.toModel() }, models)
//        }
    }
    @Test
    fun getModelById() = runBlocking {
        val modelId = UUID.randomUUID()
        val mockModel = mockModelWithQuestionsDataObject()
        val mockAnswerAlternatives = createMockAnswerAlternatives()

        coEvery { modelDao.getModelWithQuestions(modelId) } returns mockModel
        mockModel.questions.forEach {
            coEvery { modelDao.getAnswerAlternativeByQuestionId(it.questionId) } returns mockAnswerAlternatives
        }

        val returnModel = modelLocalRepository.getModelById(modelId)

        assertEquals(mockModel.questions.size, returnModel?.questions?.size)
    }
}