package com.ufms.nes.features.template.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AlternativeItem(
    label: String,
    inputValue: (String) -> Unit,
    removeResponse: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = label,
            onValueChange = {
                inputValue(it)
            }
        )
        IconButton(
            onClick = { removeResponse() }
        ) {
            Icon(
                imageVector = Icons.Default.RemoveCircle,
                contentDescription = null,
                tint = Color(0xFFCC0000)
            )
        }
    }
}
