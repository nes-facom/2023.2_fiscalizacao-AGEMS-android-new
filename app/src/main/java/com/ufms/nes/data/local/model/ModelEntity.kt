package com.ufms.nes.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ufms.nes.domain.enums.SyncState
import java.util.UUID

@Entity(tableName = "model")
data class ModelEntity(
    @PrimaryKey @ColumnInfo(name = "model_id") var modelId: UUID,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "sync_state") var syncState: SyncState
)
