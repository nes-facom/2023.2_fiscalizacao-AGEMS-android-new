package com.ufms.nes.domain.repository

import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.core.database.model.AnswerAlternativeEntity
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.core.database.model.ModelWithQuestionsDataObject
import com.ufms.nes.core.database.model.QuestionEntity
import com.ufms.nes.core.database.model.QuestionModelEntity
import com.ufms.nes.domain.model.Model
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ModelLocalRepository {

    suspend fun getAllUnSyncedModel(): List<ModelWithQuestionsDataObject>

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

}
