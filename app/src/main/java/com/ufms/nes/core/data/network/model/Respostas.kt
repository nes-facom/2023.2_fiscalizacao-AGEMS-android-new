package com.ufms.nes.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    @SerialName("id") var id: Int? = null,
    @SerialName("resposta") var response: String? = null,
    @SerialName("id_questão") var idQuestion: Int? = null
)
