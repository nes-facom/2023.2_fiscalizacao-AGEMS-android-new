package com.ufms.nes.data.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ufms.nes.core.commons.APIResult
import com.ufms.nes.core.network.ApiService
import com.ufms.nes.data.network.model.request.AddConsumeUnitDTO
import com.ufms.nes.data.network.model.request.AddFormDTO
import com.ufms.nes.data.network.model.request.AddModelDTO
import com.ufms.nes.data.network.model.response.AddFormResponseDTO
import com.ufms.nes.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.data.network.model.response.ConsumeUnitItemResponseDTO
import com.ufms.nes.data.network.model.response.ModelResponseDTO
import com.ufms.nes.data.network.model.response.ModelsResponseDTO
import com.ufms.nes.domain.repository.NetworkRepository
import com.ufms.nes.features.form.util.FormPagingSource
import com.ufms.nes.features.form.util.Form
import kotlinx.coroutines.flow.Flow
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

    override suspend fun saveForm(form: AddFormDTO): APIResult<AddFormResponseDTO> {
        return try {
            val result = service.registerForm(form)

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

    override suspend fun getModelsObjects(): APIResult<ModelsResponseDTO> {
        return try {
            val models = service.getModelsObjects()

            APIResult.Success(models)
        } catch (e: Exception) {
            APIResult.Error(null)
        }
    }

    override suspend fun getConsumeUnits(): APIResult<List<ConsumeUnitItemResponseDTO>> {
        return try {
            val models = service.getConsumeUnits()

            APIResult.Success(models)
        } catch (e: Exception) {
            APIResult.Error(null)
        }
    }

    override suspend fun saveConsumeUnit(unit: AddConsumeUnitDTO): APIResult<ConsumeUnitItemResponseDTO> {
        return try {
            val result = service.registerConsumeUnit(unit)

            APIResult.Success(result)
        } catch (e: Exception) {
            APIResult.Error(null)
        }
    }

    override suspend fun getForms(): Flow<PagingData<Form>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 1),
            pagingSourceFactory = {
                FormPagingSource(service)
            }
        ).flow
    }
}
