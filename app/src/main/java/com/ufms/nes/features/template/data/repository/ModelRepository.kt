package com.ufms.nes.features.template.data.repository

import com.ufms.nes.core.commons.Resource
import com.ufms.nes.features.template.data.model.Model
import kotlinx.coroutines.flow.Flow

interface ModelRepository {

    suspend fun getModels(): Flow<Resource<List<Model>>>

    suspend fun getModelsList(): Flow<List<Model>>

    suspend fun insertModel(model: Model)

    suspend fun getModelById(id: Int): Model?
}
