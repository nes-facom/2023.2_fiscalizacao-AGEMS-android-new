package com.ufms.nes.features.authentication.data.model

import com.ufms.nes.core.commons.LocalDateSerializer
import com.ufms.nes.features.authentication.data.enums.UserType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class User(
    @SerialName("id") val id: Long = 0,

    @SerialName("nome") private val name: String? = null,

    @SerialName("email") private val email: String? = null,

    @SerialName("senha") private val password: String? = null,

    @SerialName("senhaConfirmacao") private val passwordConfirmation: String? = null,

    @Serializable(with = LocalDateSerializer::class)
    @SerialName("dataCriacao") private val dateCreated: LocalDate? = null,

    @SerialName("cargo") private val cargo: String? = null,

    @SerialName("funcao") private val userType: String? = UserType.ADMIN.name
)
