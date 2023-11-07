package com.ufms.nes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ufms.nes.core.commons.enums.SyncState
import java.util.UUID

@Entity(tableName = "model")
data class ModelEntity(
    @PrimaryKey @ColumnInfo(name = "model_id") var modelId: UUID,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "sync_state") val syncState: SyncState
)
