package com.ufms.nes.features.template.data.repository

import com.ufms.nes.core.commons.Constants.ERROR_MESSAGE
import com.ufms.nes.core.commons.Resource
import com.ufms.nes.core.commons.toModel
import com.ufms.nes.core.commons.toModelEntity
import com.ufms.nes.core.data.network.ApiService
import com.ufms.nes.core.database.dao.ModelDao
import com.ufms.nes.core.database.dao.toResponse
import com.ufms.nes.features.template.data.mapper.toModelEntity
import com.ufms.nes.features.template.data.model.Model
import com.ufms.nes.features.template.data.model.Question
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

    override suspend fun getModelsList(): Flow<List<Model>> =
        modelDao.observeAllModels().map {
            it.toModel()
        }

    override suspend fun insertModel(model: Model) {
        val modelEntity = model.toModelEntity()
        modelDao.insertModel(modelEntity, model.questions)
    }

    override suspend fun getModelById(id: Int): Model {
        val modelQuestionsData = modelDao.getModelWithQuestions(modelId = id.toString())

        val model = Model(
            id = modelQuestionsData.modelEntity.modelId,
            name = modelQuestionsData.modelEntity.name,
            questions = modelQuestionsData.questions.zip(modelQuestionsData.modelQuestionEntities)
                .map { pair ->
                    val questionResponsesData =
                        modelDao.getQuestionWithResponses(pair.component1().questionId.toString())
                            .toResponse()

                    Question(
                        id = pair.component1().questionId,
                        question = pair.component1().question,
                        isObjective = pair.component1().isObjective,
                        portaria = pair.component1().portaria,
                        responses = questionResponsesData
                    )
                }
        )

        return model
    }
}
