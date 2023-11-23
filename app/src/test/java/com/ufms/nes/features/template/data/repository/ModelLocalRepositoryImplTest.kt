package com.ufms.nes.features.template.data.repository

import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.core.database.dao.ModelDao
import com.ufms.nes.core.database.model.ModelEntity
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
//    fun getAllUnSyncedModel() {
//    }
//
//    @Test
//    fun getModelById() {
//    }
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