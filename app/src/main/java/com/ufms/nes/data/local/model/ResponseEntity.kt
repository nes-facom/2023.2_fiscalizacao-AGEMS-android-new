package com.ufms.nes.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ufms.nes.domain.enums.SyncState
import java.util.UUID

@Entity(tableName = "response_entity")
data class ResponseEntity(
    @PrimaryKey @ColumnInfo(name = "response_id") val responseId: UUID,
    @ColumnInfo(name = "question_id") val questionId: UUID,
    @ColumnInfo(name = "form_id") val formId: UUID,
    @ColumnInfo(name = "response") var response: String? = null,
    @ColumnInfo(name = "sync_state") val syncState: SyncState
)
