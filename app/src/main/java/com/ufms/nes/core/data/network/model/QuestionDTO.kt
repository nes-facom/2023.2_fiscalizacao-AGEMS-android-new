package com.ufms.nes.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDTO(
    @SerialName("pergunta") var question: String? = null,
    @SerialName("objetiva") var objective: Boolean? = null,
    @SerialName("portaria") var portaria: String? = null,
    @SerialName("tipoRespostas") var responses: ArrayList<ResponseTypeDTO> = arrayListOf()
)