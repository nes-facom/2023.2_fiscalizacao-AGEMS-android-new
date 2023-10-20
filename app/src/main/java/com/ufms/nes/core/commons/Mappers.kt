package com.ufms.nes.core.commons

import com.ufms.nes.core.data.network.model.ModelDTO
import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.features.template.data.model.Model

fun ModelEntity.toModel(): Model = Model(
    id = modelId,
    name = name
)

fun List<ModelEntity>.toModel(): List<Model> =
    this.map {
        it.toModel()
    }

fun ModelDTO.toModelEntity(): ModelEntity = ModelEntity(
    modelId = id,
    name = name,
    synced = true
)

fun List<ModelDTO>.toModelEntity(): List<ModelEntity> =
    this.map {
        it.toModelEntity()
    }
