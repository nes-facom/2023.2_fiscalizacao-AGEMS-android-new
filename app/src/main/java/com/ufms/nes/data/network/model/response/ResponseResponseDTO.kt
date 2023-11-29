package com.ufms.nes.data.network.model.response

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ResponseResponseDTO(
    @SerialName("uuid") @Serializable(with = UUIDSerializer::class) var id: UUID? = null,
    @SerialName("uuid_local") @Serializable(with = UUIDSerializer::class) var idLocal: UUID? = null,
    @SerialName("questao") @Serializable(with = UUIDSerializer::class) var idQuestion: UUID? = null,
    @SerialName("resposta") var response: String? = null,
    @SerialName("obs") var observation: String? = null,
)