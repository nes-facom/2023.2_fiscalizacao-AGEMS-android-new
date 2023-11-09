package com.ufms.nes.core.data.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddModelDTO(
    @SerialName("nome") var name: String? = null,
    @SerialName("questoes") var questions: ArrayList<QuestionDTO> = arrayListOf()
)
