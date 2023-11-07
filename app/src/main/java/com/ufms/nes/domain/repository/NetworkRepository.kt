package com.ufms.nes.domain.repository

import com.ufms.nes.core.data.network.model.request.AddModelDTO
import com.ufms.nes.core.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.core.data.network.model.response.ModelResponseDTO
import com.ufms.nes.core.commons.APIResult
import java.util.UUID

interface NetworkRepository {

    suspend fun getModels(): APIResult<List<ModelResponseDTO>>

    suspend fun saveModel(model: AddModelDTO): APIResult<AddModelResponseDTO>

    suspend fun getModelById(modelId: UUID): APIResult<AddModelResponseDTO>
}
