package com.ufms.nes.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ufms.nes.domain.enums.SyncState
import java.util.UUID

@Entity(tableName = "question_form", primaryKeys = ["question_id", "form_id"])
data class QuestionFormEntity(
    @ColumnInfo(name = "question_id") var questionId: UUID,
    @ColumnInfo(name = "form_id") var formId: UUID,
    @ColumnInfo(name = "sync_state") var syncState: SyncState
)
