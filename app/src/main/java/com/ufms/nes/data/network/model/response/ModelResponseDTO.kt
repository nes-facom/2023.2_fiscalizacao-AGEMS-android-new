package com.ufms.nes.data.network.model.response

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ModelResponseDTO(
    @SerialName("id") @Serializable(with = UUIDSerializer::class)val id: UUID,
    @SerialName("nome") val name: String
)
