package com.ufms.nes.data.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddConsumeUnitDTO(
    @SerialName("nome") var name: String? = null,
    @SerialName("endereco") var address: String? = null,
    @SerialName("tipo") var type: String? = null,
)
