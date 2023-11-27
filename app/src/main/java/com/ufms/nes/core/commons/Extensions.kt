package com.ufms.nes.core.commons

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

inline fun <T> APIResult<T>.verifyResponse(
    onSuccess: (T) -> Unit,
    onError: () -> Unit
) {
    when (this) {
        is APIResult.Success -> onSuccess(this.data)
        is APIResult.Error -> onError()
    }
}

fun Long.convertLongToFormattedDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}