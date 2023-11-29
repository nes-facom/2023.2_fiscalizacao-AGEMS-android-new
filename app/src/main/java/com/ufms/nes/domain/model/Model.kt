package com.ufms.nes.domain.model

import java.util.UUID

data class Model(
    var id: UUID? = null,
    var idLocal: UUID? = null,
    var name: String = "",
    var questions: List<Question> = emptyList()
)
