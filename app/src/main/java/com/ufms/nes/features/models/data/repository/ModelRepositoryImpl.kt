package com.ufms.nes.features.models.data.repository

import com.ufms.nes.core.commons.Constants
import com.ufms.nes.core.commons.Resource
import com.ufms.nes.core.data.network.ApiService
import com.ufms.nes.features.models.data.model.ModelsResponseItem
import javax.inject.Inject

class ModelRepositoryImpl @Inject constructor(
    private val service: ApiService,
) : ModelRepository {

    override suspend fun getModels(): Resource<List<ModelsResponseItem>> {
        return try {
            val result = service.getModels()

            // TODO() - Salvar no banco local

            Resource.Success(data = result)
        }  catch (ex: Throwable) {
            Resource.Error(data = null, error = Constants.ERROR_MESSAGE)
        }
    }
}
