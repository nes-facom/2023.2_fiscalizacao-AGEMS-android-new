package com.ufms.nes.data.network.model.response

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ConsumeUnitItemResponseDTO(
    @SerialName("uuid") @Serializable(with = UUIDSerializer::class) var id: UUID,
    @SerialName("uuid_local") @Serializable(with = UUIDSerializer::class) var idLocal: UUID? = null,
    @SerialName("nome") var name: String? = null,
    @SerialName("endereco") var address: String? = null,
    @SerialName("tipo") var type: String? = null,
)
