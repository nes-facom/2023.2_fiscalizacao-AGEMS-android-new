package com.ufms.nes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ufms.nes.core.commons.enums.SyncState
import java.util.UUID

@Entity(tableName = "question")
data class QuestionEntity(
    @PrimaryKey @ColumnInfo(name = "question_id") val questionId: UUID,
    @ColumnInfo(name = "question") var question: String? = null,
    @ColumnInfo(name = "is_objective") var isObjective: Boolean? = null,
    @ColumnInfo(name = "ordinance") var ordinance: String? = null,
    @ColumnInfo(name = "sync_state") var syncState: SyncState
)
