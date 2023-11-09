package com.ufms.nes.domain.usecase

import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.core.commons.mappers.Mappers.toConsumeUnit
import com.ufms.nes.core.commons.mappers.Mappers.toConsumeUnitDTO
import com.ufms.nes.core.commons.mappers.Mappers.toModelDomain
import com.ufms.nes.core.commons.verifyResponse
import com.ufms.nes.core.data.network.model.request.AddModelDTO
import com.ufms.nes.core.data.network.model.request.QuestionDTO
import com.ufms.nes.core.data.network.model.request.ResponseTypeDTO
import com.ufms.nes.core.database.model.AnswerAlternativeEntity
import com.ufms.nes.core.database.model.ConsumeUnitEntity
import com.ufms.nes.core.database.model.ModelWithQuestionsDataObject
import com.ufms.nes.domain.model.ConsumeUnit
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.domain.repository.NetworkRepository
import com.ufms.nes.features.consumeunit.data.ConsumeUnitRepository
import javax.inject.Inject

class SynchronizationUseCase @Inject constructor(
    private val localRepository: ModelLocalRepository,
    private val networkRepository: NetworkRepository,
    private val consumeUnitRepository: ConsumeUnitRepository
) {

    private lateinit var alternativesEntity: List<AnswerAlternativeEntity>

    suspend fun getModels() {

        networkRepository.getModelsObjects().verifyResponse(
            onError = {},
            onSuccess = { response ->
                val modelsDTO = response.data
                val models = modelsDTO.toModelDomain()

                clearAllSyncedDataLocal()
                insertModels(models)
            }
        )
    }

    suspend fun getConsumeUnits() {

        networkRepository.getConsumeUnits().verifyResponse(
            onError = {},
            onSuccess = { consumeUnitDTOs ->
                val consumeUnits = consumeUnitDTOs.toConsumeUnit()

                consumeUnitRepository.clearAllConsumeUnitSynced()
                insertConsumeUnits(consumeUnits)
            }
        )
    }

    suspend fun sendModels() {
        val modelsDb = localRepository.getAllUnSyncedModel()


        modelsDb.forEach { modelWithQuestion ->
            sendModel(modelWithQuestion)
        }
    }

    suspend fun sendConsumeUnits() {
        val consumeUnitsDb = consumeUnitRepository.getAllUnSyncedConsumeUnits()

        consumeUnitsDb.forEach { consumeUnit ->
            sendConsumeUnit(consumeUnit)
        }
    }

    suspend fun syncModels() {
        sendModels()
        getModels()
    }


    suspend fun syncConsumeUnit() {
        sendConsumeUnits()
        getConsumeUnits()
    }

    private suspend fun sendConsumeUnit(consumeUnit: ConsumeUnitEntity) {
        val consumeUnitDTO = consumeUnit.toConsumeUnitDTO()

        networkRepository.saveConsumeUnit(consumeUnitDTO).verifyResponse(
            onError = {},
            onSuccess = {
                consumeUnit.syncState = SyncState.SYNCED
                consumeUnitRepository.updateConsumeUnit(consumeUnit)
            }
        )
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

    private suspend fun insertConsumeUnits(consumeUnits: List<ConsumeUnit>) {
        consumeUnits.forEach { consumeUnit ->
            consumeUnitRepository.insertConsumeUnit(consumeUnit, SyncState.SYNCED)
        }
    }
}
