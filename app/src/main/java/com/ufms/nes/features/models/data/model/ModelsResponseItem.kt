package com.ufms.nes.features.models.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModelsResponseItem(
    @SerialName("id")  val id: Int,
    @SerialName("nome") val name: String
)
