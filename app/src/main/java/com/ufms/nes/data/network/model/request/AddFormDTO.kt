package com.ufms.nes.data.network.model.request

import com.ufms.nes.core.commons.LocalDateSerializer
import com.ufms.nes.core.commons.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

@Serializable
data class AddFormDTO(
    @SerialName("modelo") @Serializable(with = UUIDSerializer::class) var idModel: UUID? = null,
    @SerialName("uuidLocal") @Serializable(with = UUIDSerializer::class) var idLocal: UUID? = null,
    @SerialName("unidade") @Serializable(with = UUIDSerializer::class) var idUnit: UUID? = null,
    @SerialName("respostas") var responses: ArrayList<ResponseDTO> = arrayListOf(),
    @SerialName("observacao") var observation: String? = null,
    @SerialName("imagens") var images: ArrayList<String> = arrayListOf(),
    @Serializable(with = LocalDateSerializer::class)
    @SerialName("data_criacao") private val dateCreated: LocalDate? = null,
)