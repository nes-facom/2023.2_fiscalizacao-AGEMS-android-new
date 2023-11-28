package com.ufms.nes.data.network.model.request

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ResponseDTO(
    @SerialName("uuidLocal") @Serializable(with = UUIDSerializer::class) var idLocal: UUID? = null,
    @SerialName("questao") @Serializable(with = UUIDSerializer::class) var idQuestion: UUID? = null,
    @SerialName("resposta") var response: String? = null,
)
