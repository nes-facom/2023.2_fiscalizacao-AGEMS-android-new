package com.ufms.nes.domain.usecase

import com.ufms.nes.core.commons.APIResult
import com.ufms.nes.core.commons.mappers.Mappers.toQuestion
import com.ufms.nes.core.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.core.data.network.model.response.ModelResponseDTO
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.domain.repository.NetworkRepository
import javax.inject.Inject

class SynchronizationUseCase @Inject constructor(
    private val localRepository: ModelLocalRepository,
    private val networkRepository: NetworkRepository
) {

    suspend fun updateLocalDatabaseWithBackendData() {

        networkRepository.getModels().verifyResponse(
            onError = {},
            onSuccess = { modelsId ->

                val modelsDTO = getAllModelsObjects(modelsId)
                val models = modelsDTO.toModel()

                insertModels(models)
            }
        )
    }

    private fun List<AddModelResponseDTO>.toModel(): List<Model> {
        return this.map { modelResponse ->
            Model(
                id = modelResponse.id,
                name = modelResponse.name.orEmpty(),
                questions = modelResponse.questions.toQuestion()
            )
        }
    }

    private suspend fun getAllModelsObjects(modelsId: List<ModelResponseDTO>): List<AddModelResponseDTO> {
        val modelResponseDTO: MutableList<AddModelResponseDTO> = mutableListOf()

        modelsId.forEach {
            networkRepository.getModelById(it.id).verifyResponse(
                onError = {},
                onSuccess = { response ->
                    modelResponseDTO.add(response)
                }
            )
        }

        return modelResponseDTO
    }

    private suspend fun insertModels(models: List<Model>) {
        models.forEach { model ->
            localRepository.insertModel(model)
        }
    }

}

inline fun <T> APIResult<T>.verifyResponse(
    onSuccess: (T) -> Unit,
    onError: () -> Unit
) {
    when (this) {
        is APIResult.Success -> onSuccess(this.data)
        is APIResult.Error -> onError()
    }
}
