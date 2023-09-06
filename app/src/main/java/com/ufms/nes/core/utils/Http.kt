package com.ufms.nes.core.utils

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText

class Http {
    suspend fun getHttpExceptionMessage(httpRes: HttpResponse): String {
        return httpRes.bodyAsText().substringAfter("\"error\":\"").removeSuffix("\"}")
    }
}