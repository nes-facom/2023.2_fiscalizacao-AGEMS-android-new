package com.ufms.nes.domain.repository

import androidx.paging.PagingData
import com.ufms.nes.core.commons.APIResult
import com.ufms.nes.data.network.model.request.AddConsumeUnitDTO
import com.ufms.nes.data.network.model.request.AddFormDTO
import com.ufms.nes.data.network.model.request.AddModelDTO
import com.ufms.nes.data.network.model.response.AddFormResponseDTO
import com.ufms.nes.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.data.network.model.response.ConsumeUnitItemResponseDTO
import com.ufms.nes.data.network.model.response.ModelResponseDTO
import com.ufms.nes.data.network.model.response.ModelsResponseDTO
import com.ufms.nes.features.form.util.Form
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface NetworkRepository {

    suspend fun getModels(): APIResult<List<ModelResponseDTO>>

    suspend fun saveModel(model: AddModelDTO): APIResult<AddModelResponseDTO>

    suspend fun saveForm(form: AddFormDTO): APIResult<AddFormResponseDTO>

    suspend fun getModelById(modelId: UUID): APIResult<AddModelResponseDTO>

    suspend fun getModelsObjects(): APIResult<ModelsResponseDTO>

    suspend fun getConsumeUnits(): APIResult<List<ConsumeUnitItemResponseDTO>>

    suspend fun saveConsumeUnit(unit: AddConsumeUnitDTO): APIResult<ConsumeUnitItemResponseDTO>

    suspend fun getForms(): Flow<PagingData<Form>>
}
