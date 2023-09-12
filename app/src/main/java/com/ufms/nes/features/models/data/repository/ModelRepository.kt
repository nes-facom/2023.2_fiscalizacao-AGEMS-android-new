package com.ufms.nes.features.models.data.repository

import com.ufms.nes.core.commons.Resource
import com.ufms.nes.features.models.data.model.ModelsResponseItem

interface ModelRepository {

    suspend fun getModels(): Resource<List<ModelsResponseItem>>
}
