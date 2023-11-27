package com.ufms.nes.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ufms.nes.domain.enums.SyncState
import java.util.UUID

@Entity(tableName = "form_entity")
data class FormEntity(
    @PrimaryKey @ColumnInfo(name = "form_id") var formId: UUID,
    @ColumnInfo(name = "model_id") val modelId: UUID,
    @ColumnInfo(name = "unit_id") val unitId: UUID,
    @ColumnInfo(name = "observation") val observation: String = "",
    @ColumnInfo(name = "date_created") val dateCreated: Long = 0L,
    @ColumnInfo(name = "sync_state") var syncState: SyncState
)
