package com.ufms.nes.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ufms.nes.domain.enums.SyncState
import java.util.UUID

@Entity(tableName = "question_model", primaryKeys = ["question_id", "model_id"])
data class QuestionModelEntity(
    @ColumnInfo(name = "question_id") val questionId: UUID,
    @ColumnInfo(name = "model_id") val modelId: UUID,
    @ColumnInfo(name = "sync_state") var syncState: SyncState
)
