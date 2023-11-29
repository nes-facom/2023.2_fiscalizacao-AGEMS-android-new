package com.ufms.nes.domain.model

import java.util.UUID

data class Response(
    val id: UUID? = null,
    val questionId: UUID,
    val formId: UUID? = null,
    var response: String
)
