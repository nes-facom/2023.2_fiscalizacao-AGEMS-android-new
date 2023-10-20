package com.ufms.nes.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTypeDTO(
    @SerialName("resposta") var response: String? = null
)
