package com.ufms.nes.features.models.data.repository

import com.ufms.nes.core.commons.Constants.ERROR_MESSAGE
import com.ufms.nes.core.commons.Resource
import com.ufms.nes.core.data.network.ApiService
import com.ufms.nes.core.data.network.toModelEntity
import com.ufms.nes.core.database.dao.ModelDao
import com.ufms.nes.core.database.model.toModel
import com.ufms.nes.core.model.Model
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ModelRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val modelDao: ModelDao
) : ModelRepository {

    override suspend fun getModels(): Flow<Resource<List<Model>>> = flow {

        val modelsFromDb = modelDao.observeAllModels().map {
            it.toModel()
        }.first()

        try {
            val resultApi = service.getModels()

            modelDao.clearAllModelsAndInsertModels(resultApi.toModelEntity())

            emit(Resource.Success(modelsFromDb))
        } catch (ex: Throwable) {
            emit(Resource.Error(data = modelsFromDb, error = ex.message ?: ERROR_MESSAGE))
        }
    }
}
