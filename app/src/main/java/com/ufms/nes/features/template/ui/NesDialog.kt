package com.ufms.nes.features.template.ui

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun NesDialog(
    onDismissRequest: (Boolean) -> Unit,
    @StringRes titleRes: Int,
    @StringRes bodyRes: Int,
    @StringRes confirmButtonRes: Int,
    @StringRes dismissButtonRes: Int
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest(false)
        },
        title = { Text(text = stringResource(id = titleRes)) },
        text = { Text(text = stringResource(id = bodyRes)) },
        confirmButton = {
            TextButton(onClick = {
                onDismissRequest(true)
            }) {
                Text(stringResource(id = confirmButtonRes))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest(false) }) {
                Text(stringResource(id = dismissButtonRes))
            }
        }
    )
}
