package com.ufms.nes.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddModelDTO(
    @SerialName("modelo") var name: String? = null,
    @SerialName("questoes") var questions: ArrayList<QuestionDTO> = arrayListOf()
)
