package com.ufms.nes.core.data.network.model.response

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AnswerAlternativeResponseDTO(
    @SerialName("id") @Serializable(with = UUIDSerializer::class) var id: UUID,
    @SerialName("descricao") var response: String? = null,
    @SerialName("id_questão") @Serializable(with = UUIDSerializer::class) var idQuestion: UUID
)