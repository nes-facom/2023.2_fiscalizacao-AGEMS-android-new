package com.ufms.nes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ufms.nes.core.commons.enums.SyncState
import java.util.UUID

@Entity(tableName = "answer_alternative")
data class AnswerAlternativeEntity(
    @PrimaryKey @ColumnInfo(name = "alternative_id")val alternativeId: UUID,
    @ColumnInfo(name = "question_id") var questionId: UUID,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "sync_state") var syncState: SyncState
)
