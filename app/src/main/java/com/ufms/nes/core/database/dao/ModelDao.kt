package com.ufms.nes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.core.database.model.ModelWithQuestionsDataObject
import com.ufms.nes.core.database.model.QuestionEntity
import com.ufms.nes.core.database.model.QuestionModelEntity
import com.ufms.nes.core.database.model.QuestionResponseEntity
import com.ufms.nes.core.database.model.QuestionWithResponsesDataObject
import com.ufms.nes.core.database.model.ResponseEntity
import com.ufms.nes.features.template.data.mapper.toQuestionEntity
import com.ufms.nes.features.template.data.model.Model
import com.ufms.nes.features.template.data.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface ModelDao {

    @Query("SELECT * FROM model_entity ORDER BY name")
    fun observeAllModels(): Flow<List<ModelEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertModels(list: List<ModelEntity>)

    @Query("DELETE FROM model_entity")
    suspend fun clearAllModels()

    @Transaction
    suspend fun clearAllModelsAndInsertModels(list: List<ModelEntity>) {
        clearAllModels()
        insertModels(list)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(modelEntity: ModelEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponse(responseEntity: ResponseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModelQuestionEntities(modelQuestionEntities: List<QuestionModelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionResponseEntities(questionResponseEntities: List<QuestionResponseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(questionEntity: QuestionEntity): Long

    @Query("SELECT * FROM model_entity WHERE modelId IN (:modelId)")
    suspend fun getModelById(modelId: String): ModelEntity?

    @Query("SELECT * FROM response_entity WHERE responseId IN (:responseId)")
    suspend fun getResponseById(responseId: String): ResponseEntity?

    @Transaction
    @Query("SELECT * FROM model_entity WHERE modelId IN (:modelId)")
    suspend fun getModelWithQuestions(modelId: String): ModelWithQuestionsDataObject

    @Transaction
    @Query("SELECT * FROM question_entity WHERE questionId IN (:questionId)")
    suspend fun getQuestionWithResponses(questionId: String): QuestionWithResponsesDataObject

    @Transaction
    suspend fun insertModel(
        modelEntity: ModelEntity,
        questions: List<Question>,
    ) {

        val modelQuestionEntities = mutableListOf<QuestionModelEntity>()

        val modelId = insertModel(modelEntity)

        questions.forEach { question ->
            val questionId = insertQuestion(question.toQuestionEntity())

            val questionResponseEntities = mutableListOf<QuestionResponseEntity>()

            question.responses.forEach {
                val responseId = insertResponse(ResponseEntity(response = it))

                questionResponseEntities.add(
                    QuestionResponseEntity(questionId = questionId, responseId = responseId)
                )
            }

            insertQuestionResponseEntities(questionResponseEntities)

            modelQuestionEntities.add(
                QuestionModelEntity(modelId = modelId, questionId = questionId)
            )
        }

        insertModelQuestionEntities(modelQuestionEntities)
    }
}

fun ModelWithQuestionsDataObject.toModel(): Model {
    return Model(
        id = modelEntity.modelId,
        name = modelEntity.name,
        questions = questions.zip(modelQuestionEntities).map { pair ->
            Question(
                id = pair.component1().questionId,
                question = pair.component1().question,
                isObjective = pair.component1().isObjective,
                portaria = pair.component1().portaria,
                responses = emptyList()
            )
        }
    )
}

fun QuestionWithResponsesDataObject.toResponse(): List<String> {
    return responses.zip(questionResponseEntities).mapNotNull { pair ->
        pair.first.response
    }
}
