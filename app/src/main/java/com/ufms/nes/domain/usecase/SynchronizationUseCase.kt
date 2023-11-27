package com.ufms.nes.domain.usecase

import com.ufms.nes.core.commons.mappers.Mappers.toConsumeUnit
import com.ufms.nes.core.commons.mappers.Mappers.toConsumeUnitDTO
import com.ufms.nes.core.commons.mappers.Mappers.toModelDomain
import com.ufms.nes.core.commons.mappers.Mappers.toResponseDTO
import com.ufms.nes.core.commons.verifyResponse
import com.ufms.nes.data.local.model.AnswerAlternativeEntity
import com.ufms.nes.data.local.model.ConsumeUnitEntity
import com.ufms.nes.data.local.model.FormEntity
import com.ufms.nes.data.local.model.ModelWithQuestionsDataObject
import com.ufms.nes.data.local.model.ResponseEntity
import com.ufms.nes.data.network.model.request.AddFormDTO
import com.ufms.nes.data.network.model.request.AddModelDTO
import com.ufms.nes.data.network.model.request.QuestionDTO
import com.ufms.nes.data.network.model.request.ResponseTypeDTO
import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.domain.model.ConsumeUnit
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.repository.ConsumeUnitRepository
import com.ufms.nes.domain.repository.ModelLocalRepository
import com.ufms.nes.domain.repository.NetworkRepository
import java.util.UUID
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

    suspend fun sendForms() {
        val formsDb = localRepository.getAllUnSyncedForm()

        formsDb.forEach { formEntity ->
            val responses = localRepository.getAllResponseByFormId(formEntity.formId)

            sendForm(formEntity, responses)
        }
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

    suspend fun syncForms() {
        sendForms()
    }

    private suspend fun sendConsumeUnit(consumeUnit: ConsumeUnitEntity) {
        val consumeUnitDTO = consumeUnit.toConsumeUnitDTO()

        networkRepository.saveConsumeUnit(consumeUnitDTO).verifyResponse(
            onError = {},
            onSuccess = { consumeUnitResponseDto ->
                consumeUnit.syncState = SyncState.SYNCED
                consumeUnitRepository.updateConsumeUnit(consumeUnit)

                consumeUnitResponseDto.idLocal?.let { idLocal ->
                    localRepository.updateUnitIdInForm(idLocal, consumeUnitResponseDto.id)
                }
            }
        )
    }

    private suspend fun clearAllSyncedDataLocal() {
        localRepository.clearSyncedData()
    }

    private suspend fun sendForm(formEntity: FormEntity, responses: List<ResponseEntity>) {
        val formDTO = formEntity.toFormDTO(responses)

        networkRepository.saveForm(formDTO)
            .verifyResponse(
                onError = {},
                onSuccess = { addFormResponseDTO ->

                    validateIDs(
                        current = addFormResponseDTO.idLocal,
                        new = addFormResponseDTO.id
                    ) { currentId, newId ->
                        localRepository.updateFormId(currentId, newId)
                    }

                    addFormResponseDTO.responses.forEach { responseDTO ->
                        validateIDs(
                            current = responseDTO.idLocal,
                            new = responseDTO.id
                        ) { currentId, newId ->
                            localRepository.updateResponseId(currentId, newId)
                        }
                    }
                }
            )
    }

    private suspend fun sendModel(model: ModelWithQuestionsDataObject) {
        val modelDTO = model.toAddModelDTO()

        networkRepository.saveModel(modelDTO)
            .verifyResponse(
                onError = {},
                onSuccess = { modelResponseDTO ->
                    setSyncedData(model)

                    validateIDs(
                        current = modelResponseDTO.idLocal,
                        new = modelResponseDTO.id
                    ) { currentId, newId ->
                        localRepository.updateModelIdInForm(currentId, newId)
                    }

                    modelResponseDTO.questions.forEach { questionResponseDTO ->

                        questionResponseDTO.idLocal?.let { currentQuestionId ->
                            localRepository.updateQuestionRelations(
                                currentQuestionId,
                                questionResponseDTO.id
                            )
                        }
                    }
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

    private fun FormEntity.toFormDTO(responses: List<ResponseEntity>): AddFormDTO {
        return AddFormDTO(
            idModel = this.modelId,
            idLocal = this.formId,
            idUnit = this.unitId,
            responses = ArrayList(responses.toResponseDTO()),
            observation = this.observation
        )
    }

    private suspend fun ModelWithQuestionsDataObject.toAddModelDTO(): AddModelDTO {

        val questions = this.questions.zip(this.modelQuestionEntities).map { pair ->

            alternativesEntity =
                localRepository.getAnswerAlternativeByQuestionId(pair.first.questionId)

            val alternativesDTO = alternativesEntity.map { alternative ->
                ResponseTypeDTO(response = alternative.description)
            }

            QuestionDTO(
                idLocal = pair.component1().questionId,
                question = pair.component1().question,
                objective = pair.component1().isObjective,
                portaria = pair.component1().ordinance,
                responses = ArrayList(alternativesDTO)
            )
        }

        return AddModelDTO(
            idLocal = this.modelEntity.modelId,
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

    private suspend fun validateIDs(
        current: UUID?,
        new: UUID?,
        completion: suspend (UUID, UUID) -> Unit
    ) {
        if (current != null && new != null) {
            completion(current, new)
        }
    }
}
