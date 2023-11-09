package com.ufms.nes.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ufms.nes.core.model.Model

@Entity(tableName = "models")
data class ModelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
) {
    fun toModel(): Model = Model(
        id = id,
        name = name,
    )
}

fun List<ModelEntity>.toModel() : List<Model> =
    this.map {
        it.toModel()
    }
