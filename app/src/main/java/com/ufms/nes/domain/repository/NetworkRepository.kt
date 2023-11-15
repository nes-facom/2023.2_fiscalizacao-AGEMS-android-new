package com.ufms.nes.domain.repository

import com.ufms.nes.core.commons.APIResult
import com.ufms.nes.data.network.model.request.AddConsumeUnitDTO
import com.ufms.nes.data.network.model.request.AddModelDTO
import com.ufms.nes.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.data.network.model.response.ConsumeUnitItemResponseDTO
import com.ufms.nes.data.network.model.response.ModelResponseDTO
import com.ufms.nes.data.network.model.response.ModelsResponseDTO
import java.util.UUID

interface NetworkRepository {

    suspend fun getModels(): APIResult<List<ModelResponseDTO>>

    suspend fun saveModel(model: AddModelDTO): APIResult<AddModelResponseDTO>

    suspend fun getModelById(modelId: UUID): APIResult<AddModelResponseDTO>

    suspend fun getModelsObjects(): APIResult<ModelsResponseDTO>

    suspend fun getConsumeUnits(): APIResult<List<ConsumeUnitItemResponseDTO>>

    suspend fun saveConsumeUnit(unit: AddConsumeUnitDTO): APIResult<ConsumeUnitItemResponseDTO>
}
