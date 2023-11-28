package com.ufms.nes.domain.repository

import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.data.local.model.AnswerAlternativeEntity
import com.ufms.nes.data.local.model.FormEntity
import com.ufms.nes.data.local.model.ModelEntity
import com.ufms.nes.data.local.model.ModelWithQuestionsDataObject
import com.ufms.nes.data.local.model.QuestionEntity
import com.ufms.nes.data.local.model.QuestionModelEntity
import com.ufms.nes.data.local.model.ResponseEntity
import com.ufms.nes.domain.model.Model
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ModelLocalRepository {

    suspend fun getAllUnSyncedModel(): List<ModelWithQuestionsDataObject>

    suspend fun getAllUnSyncedForm(): List<FormEntity>
    suspend fun getAllResponseByFormId(formId: UUID): List<ResponseEntity>

    suspend fun getModelsList(): Flow<List<Model>>

    suspend fun insertModel(model: Model, syncState: SyncState)

    suspend fun getModelById(id: UUID): Model?

    suspend fun getAnswerAlternativeByQuestionId(questionId: UUID): List<AnswerAlternativeEntity>

    suspend fun clearSyncedData()

    suspend fun updateModel(modelEntity: ModelEntity)
    suspend fun updateQuestionModel(questionModelEntity: QuestionModelEntity)
    suspend fun updateQuestion(questionEntity: QuestionEntity)
    suspend fun updateAnswerAlternative(questionId: UUID, syncState: SyncState)
    suspend fun updateQuestionModel(modelId: UUID, syncState: SyncState)

    suspend fun updateUnitIdInForm(unitId: UUID, newUnitId: UUID)

    suspend fun updateModelIdInForm(modelId: UUID, newModelId: UUID)

    suspend fun updateQuestionRelations(currentLocalId: UUID, newBackendId: UUID)

    suspend fun updateFormId(currentId: UUID, newId: UUID)

    suspend fun updateResponseId(currentId: UUID, newId: UUID)

}
