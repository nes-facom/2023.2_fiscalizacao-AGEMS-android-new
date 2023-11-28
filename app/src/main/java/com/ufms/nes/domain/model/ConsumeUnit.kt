package com.ufms.nes.domain.model

import java.util.UUID

data class ConsumeUnit(
    val id: UUID? = null,
    var name: String?,
    var address: String?,
    var type: String?
)
