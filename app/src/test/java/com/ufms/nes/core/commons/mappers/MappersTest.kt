package com.ufms.nes.core.commons.mappers

import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.core.commons.mappers.Mappers.toModel
import com.ufms.nes.core.commons.mappers.Mappers.toModelDomain
import com.ufms.nes.core.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.features.createMockAddModelResponseDTO
import com.ufms.nes.features.createTestModelEntity
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.UUID

class MappersTest {
    @MockK
    private lateinit var modelEntity: ModelEntity
    private lateinit var listModelResponseDTO: List<AddModelResponseDTO>

    @Before
    fun setup() {
        modelEntity = ModelEntity(
            modelId = UUID.randomUUID(),
            name = "Some Model",
            syncState = SyncState.SYNCED
        )
    }

    @Test
    fun modelEntity_toModel() = runBlocking {

        val mappedModel = modelEntity.toModel()

        assertEquals(modelEntity.modelId, mappedModel.id)
        assertEquals(modelEntity.name, mappedModel.name)
    }
    @Test
    fun modelEntity_toModelList() = runBlocking {

        val listOfTestEntities: List<ModelEntity> = List(10) {
            createTestModelEntity()
        }

        val mappedModels = listOfTestEntities.toModel()

        assertEquals(listOfTestEntities.size, mappedModels.size)

        mappedModels.forEachIndexed { index, model ->
            assertEquals(model.id, listOfTestEntities[index].modelId)
            assertEquals(model.name, listOfTestEntities[index].name)
        }

    }

    @Test
    fun addModelResponseDTOList_toModelDomain() {
        val listOfAddModelResponseDTO: List<AddModelResponseDTO> = List(10) {
            createMockAddModelResponseDTO()
        }
        val convertedToModelDomain = listOfAddModelResponseDTO.toModelDomain()

        assertEquals(listOfAddModelResponseDTO.size, convertedToModelDomain.size)

        convertedToModelDomain.forEachIndexed { index, model ->
            assertEquals(model.id, listOfAddModelResponseDTO[index].id)
            assertEquals(model.name, listOfAddModelResponseDTO[index].name)
        }
    }

    @Test
    fun toModelDomain() {
    }
}