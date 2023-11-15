package com.ufms.nes.features.consumeunit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.IconData
import com.ufms.nes.core.ui.components.SmallTopBar
import com.ufms.nes.core.ui.components.TopBarData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddConsumeUnitScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: AddConsumeUnitViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SmallTopBar(
                data = TopBarData(title = R.string.register_unit,
                    navigationIcon = Icons.Default.ArrowBack,
                    iconsActions = listOf(
                        IconData(
                            actionIcon = Icons.Default.Check,
                            onActionClick = {
                                viewModel.addConsumeUnit()
                            },
                            isVisible = true,
                            actionIconContentDescription = R.string.save_unit
                        )
                    ),
                    onNavigationClick = { onBack() }
                ),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->

        AddConsumeUnitContent(
            modifier = modifier.padding(paddingValues),
            uiState = uiState,
            viewModel = viewModel
        )

        LaunchedEffect(uiState.isConsumeUnitSaved) {
            if (uiState.isConsumeUnitSaved) {
                onBack()
            }
        }
    }
}

@Composable
fun AddConsumeUnitContent(
    modifier: Modifier,
    uiState: AddConsumeUnitUiState,
    viewModel: AddConsumeUnitViewModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(R.string.entered_name),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            value = uiState.name,
            onValueChange = { viewModel.enteredName(it) },
            supportingText = {
                if (uiState.nameIsInvalid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.required_field),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = uiState.nameIsInvalid
        )

        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(R.string.entered_address),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            value = uiState.address,
            onValueChange = { viewModel.enteredAddress(it) },
            supportingText = {
                if (uiState.addressIsInvalid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.required_field),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = uiState.addressIsInvalid
        )

        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(R.string.entered_type),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            value = uiState.type,
            onValueChange = { viewModel.enteredType(it) },
            supportingText = {
                if (uiState.typeIsInvalid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.required_field),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = uiState.typeIsInvalid
        )
    }
}