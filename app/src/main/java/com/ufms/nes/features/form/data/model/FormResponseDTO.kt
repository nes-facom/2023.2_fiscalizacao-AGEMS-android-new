package com.ufms.nes.features.form.data.model

import com.ufms.nes.core.commons.LocalDateSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class FormResponseDto(
    @SerialName("id")
    val id: Int,

    @SerialName("usuario_criacao")
    val usuario_criacao: String,

    @SerialName("data_criacao")
    val data_criacao: Long,

    @SerialName("unidade")
    val unidade: String?,
)