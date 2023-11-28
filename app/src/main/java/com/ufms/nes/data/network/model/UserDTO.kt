package com.ufms.nes.data.network.model

import com.ufms.nes.domain.enums.UserType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerialName("id") val id: Long = 0,
    @SerialName("nome") private val name: String? = null,
    @SerialName("email") private val email: String? = null,
    @SerialName("senha") private val password: String? = null,
    @SerialName("senhaConfirmacao") private val passwordConfirmation: String? = null,
    @SerialName("data_criacao") private val dateCreated: Long? = null,
    @SerialName("cargo") private val cargo: String? = null,
    @SerialName("funcao") private val userType: String? = UserType.ADMIN.name
)
