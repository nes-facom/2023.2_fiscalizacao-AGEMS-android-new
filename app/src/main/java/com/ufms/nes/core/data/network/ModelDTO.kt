package com.ufms.nes.core.data.network

import com.ufms.nes.core.database.model.ModelEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModelDTO(
    @SerialName("id") val id: Long,
    @SerialName("nome") val name: String
) {
    fun toModelEntity(): ModelEntity = ModelEntity(
        id = id,
        name = name
    )
}

fun List<ModelDTO>.toModelEntity(): List<ModelEntity> =
    this.map {
        it.toModelEntity()
    }
