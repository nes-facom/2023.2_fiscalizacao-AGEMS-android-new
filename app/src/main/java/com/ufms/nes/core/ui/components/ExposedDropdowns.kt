package com.ufms.nes.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ufms.nes.domain.model.ConsumeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StringExposedDropdown(
    modifier: Modifier = Modifier,
    items: List<String>,
    response: String,
    expanded: Boolean,
    onValueChange: (String) -> Unit,
    onExpanded: (Boolean) -> Unit,
) {

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = {
            onExpanded(!expanded)
        },
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = response,
            readOnly = true,
            onValueChange = {},
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        )

        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                onExpanded(false)
            }
        ) {
            items.forEach { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                        .defaultMinSize(minHeight = 48.dp)
                        .clickable {
                            onValueChange(item)
                            onExpanded(false)
                        }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitExposedDropdown(
    modifier: Modifier = Modifier,
    items: List<ConsumeUnit>,
    unit: String,
    expanded: Boolean,
    onSelectUnit: (ConsumeUnit) -> Unit,
    onExpanded: (Boolean) -> Unit,
) {

    ExposedDropdownMenuBox(
        modifier = modifier
            .fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = {
            onExpanded(!expanded)
        },
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = unit,
            readOnly = true,
            onValueChange = {},
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        )

        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                onExpanded(false)
            }
        ) {
            items.forEach { item ->
                Text(
                    text = item.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                        .defaultMinSize(minHeight = 48.dp)
                        .clickable {
                            onSelectUnit(item)
                            onExpanded(false)
                        }
                )
            }
        }
    }
}
