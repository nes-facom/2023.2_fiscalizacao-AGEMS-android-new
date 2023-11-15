package com.ufms.nes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ufms.nes.domain.enums.SyncState
import java.util.UUID

@Entity(tableName = "consume_unit")
data class ConsumeUnitEntity(
    @PrimaryKey @ColumnInfo(name = "unit_id") var unitId: UUID,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "address") var address: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "sync_state") var syncState: SyncState
)
