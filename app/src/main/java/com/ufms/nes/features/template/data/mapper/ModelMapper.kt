package com.ufms.nes.features.template.data.mapper

import com.ufms.nes.core.database.model.ModelEntity
import com.ufms.nes.features.template.data.model.Model

fun Model.toModelEntity(): ModelEntity {
    return ModelEntity(
        modelId = id,
        name = name
    )
}
