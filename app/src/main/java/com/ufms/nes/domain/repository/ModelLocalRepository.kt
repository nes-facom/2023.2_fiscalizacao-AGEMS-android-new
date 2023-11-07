package com.ufms.nes.domain.repository

import com.ufms.nes.core.database.model.AnswerAlternativeEntity
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.core.database.model.ModelWithQuestionsDataObject
import com.ufms.nes.domain.model.Model
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ModelLocalRepository {

    suspend fun getAllUnSyncedModel(): List<ModelWithQuestionsDataObject>

    suspend fun getModelsList(): Flow<List<Model>>

    suspend fun insertModel(model: Model)

    suspend fun getModelById(id: UUID): Model?

    suspend fun getAnswerAlternativeByQuestionId(questionId: UUID): List<AnswerAlternativeEntity>

    suspend fun clearDbAndInsertNews(
        modelsId: List<UUID>,
        questionsId: List<UUID>,
        alternativesId: List<UUID>,
        newModels: List<ModelEntity>
    )

}
