package com.ufms.nes.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "model_entity")
data class ModelEntity(
    @PrimaryKey(autoGenerate = true) val modelId: Long? = null,
    var name: String? = null,
    val synced: Boolean = false
)
