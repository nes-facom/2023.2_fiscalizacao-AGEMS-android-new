package com.ufms.nes.features.form.data.model

import kotlinx.serialization.SerialName
import java.time.LocalDateTime

data class FormResponseDto(
    @SerialName("id")
    val id: Int,

    @SerialName("user")
    val user: String,

    @SerialName("model")
    val model: String?,

    @SerialName("creationDate")
    val creationDate: LocalDateTime,

    @SerialName("unit")
    val unit: String?,
)