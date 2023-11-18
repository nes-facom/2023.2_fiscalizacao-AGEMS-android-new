package com.ufms.nes.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ufms.nes.R
import com.ufms.nes.core.ui.CardColor
import com.ufms.nes.domain.model.ConsumeUnit

@Composable
fun CardQuestion(
    modifier: Modifier = Modifier,
    items: List<String>?,
    label: String,
    response: String,
    responseIsEmptyOrBlank: Boolean,
    isExposedDropdown: Boolean,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 3.dp,
        color = CardColor
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, top = 8.dp)
        ) {
            Text(text = label)

            if (isExposedDropdown) {
                StringExposedDropdown(
                    modifier = Modifier.padding(bottom = 14.dp, top = 5.dp),
                    items = items.orEmpty(),
                    response = response,
                    expanded = expanded,
                    onValueChange = {
                        onValueChange(it)
                    },
                    onExpanded = {
                        expanded = it
                    }
                )
            } else {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    value = response,
                    onValueChange = { onValueChange(it) },
                    supportingText = {
                        if (responseIsEmptyOrBlank) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.required_field),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    isError = responseIsEmptyOrBlank
                )
            }
        }
    }
}

@Composable
fun UnitCardQuestion(
    modifier: Modifier = Modifier,
    items: List<ConsumeUnit>?,
    label: String,
    response: String,
    onValueChange: (ConsumeUnit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 3.dp,
        color = CardColor
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, top = 8.dp)
        ) {
            Text(text = label)

            UnitExposedDropdown(
                modifier = Modifier.padding(bottom = 14.dp, top = 5.dp),
                items = items as List<ConsumeUnit>,
                unit = response,
                expanded = expanded,
                onSelectUnit = {
                    onValueChange(it)
                },
                onExpanded = {
                    expanded = it
                }
            )
        }
    }
}
