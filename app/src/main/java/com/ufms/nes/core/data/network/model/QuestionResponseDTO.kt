package com.ufms.nes.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionResponseDTO(
    @SerialName("id") var id: Int? = null,
    @SerialName("idModelo") var idModel: Int? = null,
    @SerialName("pergunta") var question: String? = null,
    @SerialName("objetiva") var objective: Boolean? = null,
    @SerialName("portaria") var portaria: String? = null,
    @SerialName("respostas") var responses: ArrayList<ResponseDTO> = arrayListOf()
)