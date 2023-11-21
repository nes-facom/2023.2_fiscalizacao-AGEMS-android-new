package com.ufms.nes.data.network.model.response

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AnswerAlternativeResponseDTO(
    @SerialName("uuid") @Serializable(with = UUIDSerializer::class) var id: UUID,
    @SerialName("descricao") var response: String? = null,
    @SerialName("id_questão") @Serializable(with = UUIDSerializer::class) var idQuestion: UUID
)
