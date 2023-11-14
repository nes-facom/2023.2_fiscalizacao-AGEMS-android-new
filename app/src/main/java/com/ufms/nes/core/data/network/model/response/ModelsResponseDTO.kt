package com.ufms.nes.core.data.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModelsResponseDTO(
    @SerialName("data") val data: List<AddModelResponseDTO>
)