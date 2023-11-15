package com.ufms.nes.data.model

data class Model(
    val id: Long? = null,
    var name: String? = null,
    var questions: List<Question> = emptyList()
)
