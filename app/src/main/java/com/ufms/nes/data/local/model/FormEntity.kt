package com.ufms.nes.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "form_entity")
data class FormEntity(
    @PrimaryKey @ColumnInfo(name = "form_id") val formId: UUID,
    @ColumnInfo(name = "unit_id") val unitId: UUID,
    val observation: String = "",
    val dateCreated: Long = 0L
)
