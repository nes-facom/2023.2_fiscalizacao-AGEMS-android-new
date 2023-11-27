package com.ufms.nes.features.form.data

import com.ufms.nes.features.form.data.model.Form
import com.ufms.nes.features.form.data.model.FormResponseDto

fun FormResponseDto.mapFromEntity() = Form(
    id = this.id,
    unit = this.unidade,
    creationDate = this.data_criacao,
    user = this.usuario_criacao
)

fun List<FormResponseDto>.mapFromListModel(): List<Form> {
    return this.map {
        it.mapFromEntity()
    }
}