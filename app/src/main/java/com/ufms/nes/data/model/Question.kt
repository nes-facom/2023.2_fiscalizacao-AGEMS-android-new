package com.ufms.nes.data.model

data class Question(
    val id: Long? = null,
    var question: String? = null,
    var isObjective: Boolean? = null,
    var portaria: String? = null,
    var responses: List<String> = emptyList()
)
