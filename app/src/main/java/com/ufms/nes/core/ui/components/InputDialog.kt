package com.ufms.nes.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ufms.nes.R
import com.ufms.nes.core.commons.Constants.EMPTY

@Composable
fun InputDialog(
    modifier: Modifier = Modifier,
    setShowDialog: (Boolean) -> Unit,
    onResponse: (String) -> Unit
) {
    val response = remember { mutableStateOf(EMPTY) }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.LightGray
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = modifier.padding(20.dp)) {

                    Text(text = stringResource(id = R.string.select_question_type))

                    OutlinedTextField(
                        value = response.value,
                        onValueChange = {
                            response.value = it
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { setShowDialog(false) },
                        ) {
                            Text(text = "Cancelar")
                        }
                        Spacer(modifier = Modifier.width(30.dp))
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { onResponse(response.value) },
                        ) {
                            Text(text = "Adicionar")
                        }
                    }
                }
            }
        }
    }
}
