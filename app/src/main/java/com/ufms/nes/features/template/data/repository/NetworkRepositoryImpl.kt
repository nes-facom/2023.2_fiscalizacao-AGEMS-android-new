package com.ufms.nes.features.template.data.repository

import com.ufms.nes.core.data.network.ApiService
import com.ufms.nes.core.data.network.model.request.AddModelDTO
import com.ufms.nes.core.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.core.data.network.model.response.ModelResponseDTO
import com.ufms.nes.domain.repository.NetworkRepository
import com.ufms.nes.core.commons.APIResult
import java.util.UUID
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val service: ApiService
) : NetworkRepository {

    override suspend fun getModels(): APIResult<List<ModelResponseDTO>> {
        return try {
            val models = service.getModels()

            APIResult.Success(models)
        } catch (e: Exception) {
            APIResult.Error(null)
        }
    }

    override suspend fun saveModel(model: AddModelDTO): APIResult<AddModelResponseDTO> {
        return try {
            val result = service.registerModel(model)

            APIResult.Success(result)
        } catch (e: Exception) {
            APIResult.Error(null)
        }
    }

    override suspend fun getModelById(modelId: UUID): APIResult<AddModelResponseDTO> {
        return try {
            val modelResponse = service.getModelById(modelId)

            APIResult.Success(modelResponse)
        } catch (e: Exception) {
            APIResult.Error(null)
        }
    }
}
