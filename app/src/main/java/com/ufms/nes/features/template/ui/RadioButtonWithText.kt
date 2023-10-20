package com.ufms.nes.features.template.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.ufms.nes.core.commons.Constants

@Composable
fun RadioButtonWithText(
    selected: Boolean,
    onSelectedLabel: (String) -> Unit,
    label: String
) {
    val radioOptions = listOf(Constants.RADIO_OPTION_ONE, Constants.RADIO_OPTION_TWO)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Row(
        Modifier
            .fillMaxWidth()
            .height(44.dp)
            .selectable(
                selected = selected,
                onClick = {
                    onOptionSelected(label)
                    onSelectedLabel(selectedOption)
                },
                role = Role.RadioButton
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (label == selectedOption),
            onClick = null
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
