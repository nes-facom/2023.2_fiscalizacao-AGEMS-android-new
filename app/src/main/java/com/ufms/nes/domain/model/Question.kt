package com.ufms.nes.domain.model

import java.util.UUID

data class Question(
    val id: UUID? = null,
    val idLocal: UUID? = null,
    var question: String? = null,
    var isObjective: Boolean? = null,
    var portaria: String? = null,
    var responses: List<AnswerAlternative> = emptyList()
)
