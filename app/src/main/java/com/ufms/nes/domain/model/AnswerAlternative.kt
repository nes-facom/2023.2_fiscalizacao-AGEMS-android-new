package com.ufms.nes.domain.model

import java.util.UUID

data class AnswerAlternative(
    val id: UUID? = null,
    val questionId: UUID? = null,
    val description: String?,
)