package com.ufms.nes.features.form.data

import com.ufms.nes.features.form.data.model.Form
import com.ufms.nes.features.form.data.model.FormResponseDto

fun FormResponseDto.mapFromEntity() = Form(
    id = this.id,
    model = this.model,
    unit = this.unit,
    creationDate = this.creationDate,
    user = this.user
)

fun Form.mapFromDomain() = FormResponseDto(
    id = this.id,
    model = this.model,
    unit = this.unit,
    creationDate = this.creationDate,
    user = this.user
)

fun List<FormResponseDto>.mapFromListModel(): List<Form> {
    return this.map {
        it.mapFromEntity()
    }
}

fun List<Form>.mapFromListDomain(): List<FormResponseDto> {
    return this.map {
        it.mapFromDomain()
    }
}