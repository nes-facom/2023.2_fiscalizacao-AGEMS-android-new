package com.ufms.nes.domain.usecase

import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.core.commons.mappers.Mappers.toModelDomain
import com.ufms.nes.core.commons.verifyResponse
import com.ufms.nes.core.data.network.model.request.AddModelDTO
import com.ufms.nes.core.data.network.model.request.QuestionDTO
import com.ufms.nes.core.data.network.model.request.ResponseTypeDTO
import com.ufms.nes.core.database.model.AnswerAlternativeEntity
import com.ufms.nes.core.database.model.ModelWithQuestionsDataObject
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.domain.repository.NetworkRepository
import javax.inject.Inject

class SynchronizationUseCase @Inject constructor(
    private val localRepository: ModelLocalRepository,
    private val networkRepository: NetworkRepository
) {

    private lateinit var alternativesEntity: List<AnswerAlternativeEntity>

    suspend fun updateLocalDatabaseWithBackendData() {

        networkRepository.getModelsObjects().verifyResponse(
            onError = {},
            onSuccess = { response ->
                val modelsDTO = response.data
                val models = modelsDTO.toModelDomain()

                insertModels(models)
            }
        )
    }

    suspend fun sendLocalDataFromBackend() {
        val modelsDb = localRepository.getAllUnSyncedModel()


        modelsDb.forEach { modelWithQuestion ->
            sendModel(modelWithQuestion)
        }
    }

    suspend fun sync() {
        sendLocalDataFromBackend()
        clearAllSyncedDataLocal()
        updateLocalDatabaseWithBackendData()
    }

    private suspend fun clearAllSyncedDataLocal() {
        localRepository.clearSyncedData()
    }

    private suspend fun sendModel(model: ModelWithQuestionsDataObject) {
        val modelDTO = model.toAddModelDTO()

        networkRepository.saveModel(modelDTO)
            .verifyResponse(
                onError = {},
                onSuccess = {
                    setSyncedData(model)
                }
            )
    }

    private suspend fun setSyncedData(model: ModelWithQuestionsDataObject) {
        val modelLocal = model.modelEntity
        modelLocal.syncState = SyncState.SYNCED
        localRepository.updateModel(modelLocal)

        localRepository.updateQuestionModel(
            modelId = model.modelEntity.modelId,
            syncState = SyncState.SYNCED
        )

        model.questions.forEach { questionEntity ->
            questionEntity.syncState = SyncState.SYNCED
            localRepository.updateQuestion(questionEntity)

            localRepository.updateAnswerAlternative(
                questionId = questionEntity.questionId,
                syncState = SyncState.SYNCED
            )
        }
    }

    private suspend fun ModelWithQuestionsDataObject.toAddModelDTO(): AddModelDTO {

        val questions = this.questions.zip(this.modelQuestionEntities).map { pair ->

            alternativesEntity =
                localRepository.getAnswerAlternativeByQuestionId(pair.first.questionId)

            val alternativesDTO = alternativesEntity.map { alternative ->
                ResponseTypeDTO(response = alternative.description)
            }

            QuestionDTO(
                question = pair.component1().question,
                objective = pair.component1().isObjective,
                portaria = pair.component1().ordinance,
                responses = ArrayList(alternativesDTO)
            )
        }

        return AddModelDTO(
            name = this.modelEntity.name,
            questions = ArrayList(questions)
        )
    }

    private suspend fun insertModels(models: List<Model>) {
        models.forEach { model ->
            localRepository.insertModel(model, SyncState.SYNCED)
        }
    }
}
