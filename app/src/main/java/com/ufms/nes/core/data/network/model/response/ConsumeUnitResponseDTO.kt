package com.ufms.nes.core.data.network.model.response

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ConsumeUnitItemResponseDTO(
    @SerialName("id") @Serializable(with = UUIDSerializer::class) var id: UUID,
    @SerialName("nome") var name: String,
    @SerialName("endereco") var address: String,
    @SerialName("tipo") var type: String,
)
