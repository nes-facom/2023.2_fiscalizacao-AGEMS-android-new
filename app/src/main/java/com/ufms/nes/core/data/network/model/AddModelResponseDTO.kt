package com.ufms.nes.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddModelResponseDTO(
    @SerialName("id") var id: Int? = null,
    @SerialName("nome") var name: String? = null,
    @SerialName("questões") var questions: ArrayList<QuestionResponseDTO> = arrayListOf()
)
