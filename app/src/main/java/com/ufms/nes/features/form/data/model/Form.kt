package com.ufms.nes.features.form.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Form(
    @SerialName("id")
    val id: Int,

    @SerialName("user")
    val user: String,

    @SerialName("creationDate")
//    @Serializable(with = LocalDateSerializer::class)
    val creationDate: Long,

    @SerialName("unit")
    val unit: String?
)
