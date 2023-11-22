package com.ufms.nes.data.network.model.request

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AddConsumeUnitDTO(
    @SerialName("uuidLocal") @Serializable(with = UUIDSerializer::class) var idLocal: UUID? = null,
    @SerialName("nome") var name: String? = null,
    @SerialName("endereco") var address: String? = null,
    @SerialName("tipo") var type: String? = null,
)
