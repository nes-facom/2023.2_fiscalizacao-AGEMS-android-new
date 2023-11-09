package com.ufms.nes.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModelDTO(
    @SerialName("id") val id: Long,
    @SerialName("nome") val name: String
)
