package com.ufms.nes.domain.model

import java.util.Calendar
import java.util.UUID

data class Form(
    val id: UUID? = null,
    val unitId: UUID,
    val responses: List<Response> = emptyList(),
    val observation: String = "",
    val dateCreated: Long = Calendar.getInstance().timeInMillis
)
