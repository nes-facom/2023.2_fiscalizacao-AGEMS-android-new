package com.ufms.nes.data.network.model.response

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class QuestionResponseDTO(
    @SerialName("uuid") @Serializable(with = UUIDSerializer::class) var id: UUID,
    @SerialName("uuid_local") @Serializable(with = UUIDSerializer::class) var idLocal: UUID? = null,
    @SerialName("pergunta") var question: String? = null,
    @SerialName("objetiva") var objective: Boolean? = null,
    @SerialName("portaria") var portaria: String? = null,
    @SerialName("respostas") var responses: ArrayList<AnswerAlternativeResponseDTO> = arrayListOf()
)