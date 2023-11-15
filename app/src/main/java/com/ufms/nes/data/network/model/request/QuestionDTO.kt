package com.ufms.nes.data.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDTO(
    @SerialName("pergunta") var question: String? = null,
    @SerialName("objetiva") var objective: Boolean? = null,
    @SerialName("portaria") var portaria: String? = null,
    @SerialName("alternativaRespostas") var responses: ArrayList<ResponseTypeDTO> = arrayListOf()
)