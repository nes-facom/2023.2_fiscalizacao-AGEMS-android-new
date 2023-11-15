package com.ufms.nes.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "response_entity")
data class ResponseEntity(
    @PrimaryKey(autoGenerate = true) val responseId: Long? = null,
    var response: String? = null,
    val synced: Boolean = false
)
