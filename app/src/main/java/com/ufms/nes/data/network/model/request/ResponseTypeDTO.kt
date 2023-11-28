package com.ufms.nes.data.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTypeDTO(
    @SerialName("descricao") var response: String? = null
)
