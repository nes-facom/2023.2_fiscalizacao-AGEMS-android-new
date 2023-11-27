package com.ufms.nes.data.network.model.response

import com.ufms.nes.core.commons.LocalDateSerializer
import com.ufms.nes.core.commons.UUIDSerializer
import com.ufms.nes.data.network.model.UserDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

@Serializable
data class AddFormResponseDTO(
    @SerialName("uuid") @Serializable(with = UUIDSerializer::class) var id: UUID? = null,
    @SerialName("uuid_local") @Serializable(with = UUIDSerializer::class) var idLocal: UUID? = null,
    @SerialName("modelo") @Serializable(with = UUIDSerializer::class) var idModel: UUID? = null,
    @SerialName("unidade") var unit: ConsumeUnitItemResponseDTO? = null,
    @SerialName("respostas") var responses: ArrayList<ResponseResponseDTO> = arrayListOf(),
    @SerialName("observacao") var observation: String? = null,
    @SerialName("imagens") var images: ArrayList<String> = arrayListOf(),
    @Serializable(with = LocalDateSerializer::class)
    @SerialName("data_criacao") private val dateCreated: LocalDate? = null,
    @SerialName("usuario") private val user: UserDTO? = null,
)