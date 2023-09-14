package com.ufms.nes.features.models.data.repository

import com.ufms.nes.core.commons.Resource
import com.ufms.nes.core.model.Model
import kotlinx.coroutines.flow.Flow

interface ModelRepository {

    suspend fun getModels(): Flow<Resource<List<Model>>>
}
