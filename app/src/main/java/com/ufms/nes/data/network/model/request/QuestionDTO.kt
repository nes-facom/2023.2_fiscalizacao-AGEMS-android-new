package com.ufms.nes.data.network.model.request

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class QuestionDTO(
    @SerialName("uuidLocal") @Serializable(with = UUIDSerializer::class) var idLocal: UUID? = null,
    @SerialName("pergunta") var question: String? = null,
    @SerialName("objetiva") var objective: Boolean? = null,
    @SerialName("portaria") var portaria: String? = null,
    @SerialName("alternativaRespostas") var responses: ArrayList<ResponseTypeDTO> = arrayListOf()
)
