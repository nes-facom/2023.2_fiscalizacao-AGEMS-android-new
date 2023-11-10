package com.ufms.nes.core.commons

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertLongToFormattedDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}