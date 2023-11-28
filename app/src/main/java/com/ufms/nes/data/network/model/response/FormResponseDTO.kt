package com.ufms.nes.data.network.model.response

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class FormResponseDto(
    @SerialName("id")
    @Serializable(with = UUIDSerializer::class) var id: UUID,
    @SerialName("usuario_criacao") val userCreated: String?,
    @SerialName("data_criacao") val dateCreated: Long?,
    @SerialName("unidade") val unit: String?,
)
