package com.ufms.nes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ufms.nes.core.commons.enums.SyncState
import com.ufms.nes.core.database.model.AnswerAlternativeEntity
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.core.database.model.ModelWithQuestionsDataObject
import com.ufms.nes.core.database.model.QuestionEntity
import com.ufms.nes.core.database.model.QuestionModelEntity
import com.ufms.nes.domain.model.AnswerAlternative
import com.ufms.nes.domain.model.Model
import com.ufms.nes.domain.model.Question
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ModelDao {

    /**
     * GET
     */
    @Query("SELECT * FROM model ORDER BY name")
    fun observeAllModels(): Flow<List<ModelEntity>>

    @Query("SELECT * FROM model WHERE sync_state = :syncState")
    fun findAllBySyncState(syncState: SyncState): List<ModelEntity>

    @Query("SELECT * FROM answer_alternative WHERE question_id IN (:questionId)")
    suspend fun getAnswerAlternativeByQuestionId(questionId: UUID): List<AnswerAlternativeEntity>

    @Transaction
    @Query("SELECT * FROM model WHERE model_id IN (:modelId)")
    suspend fun getModelWithQuestions(modelId: UUID): ModelWithQuestionsDataObject

    @Transaction
    @Query("SELECT * FROM model WHERE sync_state = :syncState")
    suspend fun getModelsWithQuestions(syncState: SyncState): List<ModelWithQuestionsDataObject>

    /**
     * CLEAR
     */
    @Query("DELETE FROM model WHERE model_id NOT IN (:modelsId)")
    suspend fun clearAllModelsUnSynced(modelsId: List<UUID>)

    @Query("DELETE FROM answer_alternative WHERE alternative_id NOT IN (:alternativesId)")
    suspend fun clearAllAlternativeUnSynced(alternativesId: List<UUID>)

    @Query("DELETE FROM question_model WHERE question_id NOT IN (:questionsId)")
    suspend fun clearAllQuestionUnSynced(questionsId: List<UUID>)

    @Query("DELETE FROM question_model WHERE question_id NOT IN (:questionsId)")
    suspend fun clearAllQuestionModelUnSynced(questionsId: List<UUID>)

    @Transaction
    suspend fun clearUnSyncedDataBase(
        modelsId: List<UUID>,
        questionsId: List<UUID>,
        alternativesId: List<UUID>,
        newModels: List<ModelEntity>
    ) {
        clearAllModelsUnSynced(modelsId)
        clearAllAlternativeUnSynced(alternativesId)
        clearAllQuestionUnSynced(questionsId)
        clearAllQuestionModelUnSynced(questionsId)

        insertModels(newModels)
    }

    /**
     * INSERT
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertModels(list: List<ModelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(modelEntity: ModelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswerAlternative(answerAlternativeEntity: AnswerAlternativeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModelQuestionEntities(modelQuestionEntities: List<QuestionModelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(questionEntity: QuestionEntity)

    @Transaction
    suspend fun insertModel(model: Model) {
        val modelID = model.id ?: UUID.randomUUID()
        val modelEntity = ModelEntity(
            modelId = modelID,
            name = model.name,
            syncState = SyncState.EDITED
        )

        insertModel(modelEntity)

        // Insert Questions
        val modelQuestionEntities = insertQuestions(model.questions, modelID)
        insertModelQuestionEntities(modelQuestionEntities)
    }

    private suspend fun insertQuestions(
        questions: List<Question>,
        modelId: UUID
    ): List<QuestionModelEntity> {
        val modelQuestionEntities = mutableListOf<QuestionModelEntity>()

        questions.forEach { question ->
            val questionId = question.id ?: UUID.randomUUID()
            val questionEntity = QuestionEntity(
                questionId = questionId,
                question = question.question,
                ordinance = question.portaria,
                isObjective = question.isObjective
            )

            insertQuestion(questionEntity)

            modelQuestionEntities.add(
                QuestionModelEntity(
                    modelId = modelId, questionId = questionId
                )
            )

            // Insert Answer Alternatives
            insertAnswerAlternatives(question.responses, questionId)
        }

        return modelQuestionEntities
    }

    private suspend fun insertAnswerAlternatives(
        answerAlternatives: List<AnswerAlternative>,
        questionId: UUID
    ) {
        answerAlternatives.forEach { alternative ->
            val localId = alternative.id ?: UUID.randomUUID()
            val answerAlternativeEntity = AnswerAlternativeEntity(
                alternativeId = localId,
                questionId = questionId,
                description = alternative.description
            )

            insertAnswerAlternative(answerAlternativeEntity)
        }
    }
}
