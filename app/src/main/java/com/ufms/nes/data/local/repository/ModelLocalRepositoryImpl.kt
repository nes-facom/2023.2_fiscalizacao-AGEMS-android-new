package com.ufms.nes.data.local.repository

import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.core.commons.mappers.Mappers.toModel
import com.ufms.nes.core.database.dao.ModelDao
import com.ufms.nes.data.local.model.AnswerAlternativeEntity
import com.ufms.nes.data.local.model.ModelEntity
import com.ufms.nes.data.local.model.ModelWithQuestionsDataObject
import com.ufms.nes.data.local.model.QuestionEntity
import com.ufms.nes.data.local.model.QuestionModelEntity
import com.ufms.nes.domain.model.AnswerAlternative
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.model.Question
import com.ufms.nes.domain.repository.ModelLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class ModelLocalRepositoryImpl @Inject constructor(
    private val modelDao: ModelDao,
) : ModelLocalRepository {

    override suspend fun getModelsList(): Flow<List<Model>> =
        modelDao.observeAllModels().map {
            it.toModel()
        }

    override suspend fun insertModel(model: Model, syncState: SyncState) {
        modelDao.insertModel(model, syncState = syncState)
    }

    override suspend fun getAllUnSyncedModel(): List<ModelWithQuestionsDataObject> {
        return modelDao.getModelsWithQuestions(SyncState.EDITED)
    }

    override suspend fun getModelById(id: UUID): Model {
        val modelQuestionsData = modelDao.getModelWithQuestions(modelId = id)

        val model = Model(
            id = modelQuestionsData.modelEntity.modelId,
            name = modelQuestionsData.modelEntity.name,
            questions = modelQuestionsData.questions.zip(modelQuestionsData.modelQuestionEntities)
                .map { pair ->
                    val questionResponsesData =
                        modelDao.getAnswerAlternativeByQuestionId(pair.component1().questionId)

                    Question(
                        id = pair.component1().questionId,
                        question = pair.component1().question,
                        isObjective = pair.component1().isObjective,
                        portaria = pair.component1().ordinance,
                        responses = questionResponsesData.map {
                            AnswerAlternative(
                                id = it.alternativeId,
                                questionId = it.questionId,
                                description = it.description
                            )
                        }
                    )
                }
        )

        return model
    }

    override suspend fun getAnswerAlternativeByQuestionId(questionId: UUID): List<AnswerAlternativeEntity> {
        return modelDao.getAnswerAlternativeByQuestionId(questionId)
    }

    override suspend fun clearSyncedData() {
        modelDao.clearSyncedData()
    }

    override suspend fun updateModel(modelEntity: ModelEntity) {
        modelDao.updateModel(modelEntity)
    }

    override suspend fun updateQuestionModel(questionModelEntity: QuestionModelEntity) {
        modelDao.updateQuestionModel(questionModelEntity)
    }

    override suspend fun updateQuestionModel(modelId: UUID, syncState: SyncState) {
        modelDao.updateQuestionModel(modelId, syncState)
    }

    override suspend fun updateQuestion(questionEntity: QuestionEntity) {
        modelDao.updateQuestion(questionEntity)
    }

    override suspend fun updateAnswerAlternative(questionId: UUID, syncState: SyncState) {
        modelDao.updateAnswerAlternative(questionId, syncState)
    }

    override suspend fun updateQuestionForm(questionId: UUID, newQuestionId: UUID) {
        modelDao.updateQuestionForm(questionId, newQuestionId)
    }
}
