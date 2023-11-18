package com.ufms.nes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.ufms.nes.data.local.model.FormEntity
import com.ufms.nes.data.local.model.QuestionFormEntity
import com.ufms.nes.data.local.model.ResponseEntity
import com.ufms.nes.domain.enums.SyncState
import com.ufms.nes.domain.model.Form
import com.ufms.nes.domain.model.Response
import java.util.UUID

@Dao
interface FormDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForm(formEntity: FormEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponse(responseEntity: ResponseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormQuestionEntities(formQuestionEntities: List<QuestionFormEntity>)

    @Transaction
    suspend fun insertForm(form: Form, syncState: SyncState) {
        val formId = form.id ?: UUID.randomUUID()

        val formEntity = FormEntity(
            formId = formId,
            unitId = form.unitId,
            observation = form.observation,
            dateCreated = form.dateCreated
        )

        insertForm(formEntity)

        val formQuestionEntities = insertResponses(form.responses, formId, syncState)
        insertFormQuestionEntities(formQuestionEntities)
    }

    private suspend fun insertResponses(
        responses: List<Response>,
        formId: UUID,
        syncState: SyncState
    ): List<QuestionFormEntity> {
        val formQuestionEntities = mutableListOf<QuestionFormEntity>()

        responses.forEach { response ->
            val responseId = response.id ?: UUID.randomUUID()
            val responseEntity = ResponseEntity(
                responseId = responseId,
                questionId = response.questionId,
                formId = formId,
                response = response.response,
                syncState = syncState
            )

            insertResponse(responseEntity)

            formQuestionEntities.add(
                QuestionFormEntity(
                    formId = formId, questionId = response.questionId, syncState = syncState
                )
            )
        }

        return formQuestionEntities
    }
}
