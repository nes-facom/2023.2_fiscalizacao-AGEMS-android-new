package com.ufms.nes.features.form.data

import com.ufms.nes.features.form.data.model.Form
import com.ufms.nes.features.form.data.model.FormResponseDto

fun FormResponseDto.mapFromEntity() = Form(
    id = this.id,
    unit = this.unit,
    creationDate = this.dateCreated,
    user = this.userCreated
)

fun List<FormResponseDto>.mapFromListModel(): List<Form> {
    return this.map {
        it.mapFromEntity()
    }
}
