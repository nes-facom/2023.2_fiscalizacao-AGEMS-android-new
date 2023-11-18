package com.ufms.nes.features.form.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.IconData
import com.ufms.nes.core.ui.components.SmallTopBar
import com.ufms.nes.core.ui.components.TopBarData
import com.ufms.nes.core.ui.components.CardQuestion
import com.ufms.nes.features.form.viewmodel.CreateFormUiState
import com.ufms.nes.features.form.viewmodel.CreateFormViewModel
import com.ufms.nes.core.ui.components.UnitCardQuestion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFormScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onFormSaved: () -> Unit,
    viewModel: CreateFormViewModel
) {

    Scaffold(
        topBar = {
            SmallTopBar(
                data = TopBarData(title = R.string.fill_out_form,
                    navigationIcon = Icons.Default.ArrowBack,
                    iconsActions = listOf(
                        IconData(
                            actionIcon = Icons.Default.Check,
                            onActionClick = {
                                viewModel.addForm()
                            },
                            isVisible = true,
                            actionIconContentDescription = R.string.fill_out_form
                        )
                    ),
                    onNavigationClick = { onBackClick() }
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
        val uiState = viewModel.createFormUiState.collectAsStateWithLifecycle()

        CreateFormContent(
            modifier = modifier.padding(paddingValues),
            uiState = uiState,
            viewModel = viewModel,
        )

        LaunchedEffect(uiState.value.formSaved) {
            if (uiState.value.formSaved) {
                onFormSaved()
            }
        }
    }

}

@Composable
fun CreateFormContent(
    modifier: Modifier = Modifier,
    uiState: State<CreateFormUiState>,
    viewModel: CreateFormViewModel
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        UnitCardQuestion(
            label = stringResource(id = R.string.unit),
            items = uiState.value.units,
            response = uiState.value.unit?.name.orEmpty(),
            onValueChange = { viewModel.enteredUnit(it) },
        )

        uiState.value.modelSelected?.questions?.forEachIndexed { index, question ->

            Spacer(modifier = Modifier.height(10.dp))

            val alternativesDescription = question.responses.mapNotNull { it.description }

            val resp = uiState.value.responses[index].response.value

            CardQuestion(
                items = alternativesDescription,
                label = question.question.orEmpty(),
                response = resp,
                responseIsEmptyOrBlank = false, // TODO()
                isExposedDropdown = question.isObjective ?: false,
                onValueChange = {
                    viewModel.editResponse(
                        index = index,
                        response = it
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        CardQuestion(
            label = stringResource(id = R.string.observation),
            items = null,
            response = uiState.value.observation,
            responseIsEmptyOrBlank = false,
            isExposedDropdown = false,
            onValueChange = { viewModel.enteredObservation(it) },
        )
    }
}
