package com.ufms.nes.features.template.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufms.nes.R
import com.ufms.nes.core.commons.Constants.RADIO_OPTION_ONE
import com.ufms.nes.core.commons.Constants.RADIO_OPTION_TWO
import com.ufms.nes.core.ui.components.IconData
import com.ufms.nes.core.ui.components.SmallTopBar
import com.ufms.nes.core.ui.components.TopBarData
import com.ufms.nes.features.template.presentation.viewmodel.AddModelViewModel
import com.ufms.nes.features.template.ui.AlternativeItem
import com.ufms.nes.features.template.ui.OutlinedBoxClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: AddModelViewModel
) {
    val uiState by viewModel.currentQuestion.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.clearCurrentQuestion()
        onBack()
    }

    Scaffold(
        topBar = {
            SmallTopBar(
                data = TopBarData(title = R.string.add_question,
                    navigationIcon = Icons.Default.ArrowBack,
                    iconsActions = listOf(
                        IconData(
                            actionIcon = Icons.Default.Check,
                            onActionClick = {
                                viewModel.addQuestion().let {
                                    if (it) {
                                        onBack()
                                    }
                                }
                            },
                            isVisible = true,
                            actionIconContentDescription = R.string.save_model
                        )
                    ),
                    onNavigationClick = {
                        viewModel.clearCurrentQuestion().apply {
                            onBack()
                        }
                    }
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

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = stringResource(R.string.entered_question),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                value = uiState.question,
                onValueChange = { viewModel.enteredQuestion(it) },
                supportingText = {
                    if (uiState.questionIsEmpty) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Campo obrigatório",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                isError = uiState.questionIsEmpty
            )

            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = stringResource(R.string.entered_ordinance),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                value = uiState.ordinance,
                onValueChange = { viewModel.enteredOrdinance(it) },
                supportingText = {
                    if (uiState.ordinanceIsEmpty) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Campo obrigatório",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                isError = uiState.ordinanceIsEmpty
            )

            Text(
                text = stringResource(id = R.string.select_question_type),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )


            Column(Modifier.selectableGroup()) {
                RadioWithLabel(
                    selected = !uiState.isObjective,
                    text = RADIO_OPTION_ONE,
                    isObjective = {
                        viewModel.enteredObjective(it)
                    }
                )
                RadioWithLabel(
                    selected = uiState.isObjective,
                    text = RADIO_OPTION_TWO,
                    isObjective = {
                        viewModel.enteredObjective(it)
                    }
                )
            }

            if (uiState.isObjective) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = stringResource(R.string.add_questions),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedBoxClickable(onClick = {
                    viewModel.addEmptyResponse()
                })

                if (uiState.isObjectiveAndEmpty) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        text = "Adicione ao menos uma alternativa",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                uiState.responses.forEachIndexed { index, _ ->
                    Spacer(modifier = Modifier.height(5.dp))
                    AlternativeItem(
                        label = uiState.responses[index],
                        inputValue = { viewModel.editResponse(index, it) },
                        removeResponse = { viewModel.removeResponse(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun RadioWithLabel(
    selected: Boolean,
    text: String,
    isObjective: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(44.dp)
            .selectable(
                selected = selected,
                onClick = {
                    if (text == RADIO_OPTION_TWO) {
                        isObjective(true)
                    } else {
                        isObjective(false)
                    }
                },
                role = Role.RadioButton
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
