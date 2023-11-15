package com.ufms.nes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.data.local.model.AnswerAlternativeEntity
import com.ufms.nes.data.local.model.ModelEntity
import com.ufms.nes.data.local.model.ModelWithQuestionsDataObject
import com.ufms.nes.data.local.model.QuestionEntity
import com.ufms.nes.data.local.model.QuestionModelEntity
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
    @Transaction
    suspend fun clearSyncedData() {
        val syncState = SyncState.SYNCED
        clearModels(syncState)
        clearQuestions(syncState)
        clearQuestionModel(syncState)
        clearAlternative(syncState)
    }

    @Query("DELETE FROM model WHERE sync_state = :syncState")
    suspend fun clearModels(syncState: SyncState)

    @Query("DELETE FROM question WHERE sync_state = :syncState")
    suspend fun clearQuestions(syncState: SyncState)

    @Query("DELETE FROM question_model WHERE sync_state = :syncState")
    suspend fun clearQuestionModel(syncState: SyncState)

    @Query("DELETE FROM answer_alternative WHERE sync_state = :syncState")
    suspend fun clearAlternative(syncState: SyncState)

    /**
     * INSERT
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(modelEntity: ModelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswerAlternative(answerAlternativeEntity: AnswerAlternativeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModelQuestionEntities(modelQuestionEntities: List<QuestionModelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(questionEntity: QuestionEntity)

    @Transaction
    suspend fun insertModel(model: Model, syncState: SyncState) {
        val modelID = model.id ?: UUID.randomUUID()
        val modelEntity = ModelEntity(
            modelId = modelID,
            name = model.name,
            syncState = syncState
        )

        insertModel(modelEntity)

        // Insert Questions
        val modelQuestionEntities = insertQuestions(model.questions, modelID, syncState)
        insertModelQuestionEntities(modelQuestionEntities)
    }

    private suspend fun insertQuestions(
        questions: List<Question>,
        modelId: UUID,
        syncState: SyncState
    ): List<QuestionModelEntity> {
        val modelQuestionEntities = mutableListOf<QuestionModelEntity>()

        questions.forEach { question ->
            val questionId = question.id ?: UUID.randomUUID()
            val questionEntity = QuestionEntity(
                questionId = questionId,
                question = question.question,
                ordinance = question.portaria,
                isObjective = question.isObjective,
                syncState = syncState
            )

            insertQuestion(questionEntity)

            modelQuestionEntities.add(
                QuestionModelEntity(
                    modelId = modelId, questionId = questionId, syncState = syncState
                )
            )

            // Insert Answer Alternatives
            insertAnswerAlternatives(question.responses, questionId, syncState)
        }

        return modelQuestionEntities
    }

    private suspend fun insertAnswerAlternatives(
        answerAlternatives: List<AnswerAlternative>,
        questionId: UUID,
        syncState: SyncState
    ) {
        answerAlternatives.forEach { alternative ->
            val localId = alternative.id ?: UUID.randomUUID()
            val answerAlternativeEntity = AnswerAlternativeEntity(
                alternativeId = localId,
                questionId = questionId,
                description = alternative.description,
                syncState = syncState
            )

            insertAnswerAlternative(answerAlternativeEntity)
        }
    }

    /**
     * UPDATE
     */
    @Update
    fun updateModel(model: ModelEntity)

    @Update
    fun updateQuestion(question: QuestionEntity)

    @Update
    fun updateQuestionModel(model: QuestionModelEntity)

    @Query("UPDATE answer_alternative SET sync_state = :syncState WHERE question_id = :questionId")
    fun updateAnswerAlternative(questionId: UUID, syncState: SyncState)

    @Query("UPDATE question_model SET sync_state = :syncState WHERE model_id = :modelId")
    fun updateQuestionModel(modelId: UUID, syncState: SyncState)
}
