package com.ufms.nes.core.utils

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
suspend fun HttpResponse.getHttpExceptionMessage(): String = this.bodyAsText().substringAfter("\"error\":\"").removeSuffix("\"}")