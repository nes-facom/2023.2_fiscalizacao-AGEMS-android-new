package com.ufms.nes.data.network.model.response

import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AddModelResponseDTO(
    @SerialName("id") @Serializable(with = UUIDSerializer::class) var id: UUID,
    @SerialName("nome") var name: String? = null,
    @SerialName("questões") var questions: ArrayList<QuestionResponseDTO> = arrayListOf()
)
