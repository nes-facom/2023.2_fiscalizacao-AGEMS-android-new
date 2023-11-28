package com.ufms.nes.features.form.data.model

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Form(
    @SerialName("id")
    @Serializable(with = UUIDSerializer::class) var id: UUID,
    @SerialName("user") val user: String?,
    @SerialName("creationDate") val creationDate: Long?,
    @SerialName("unit") val unit: String?
)
