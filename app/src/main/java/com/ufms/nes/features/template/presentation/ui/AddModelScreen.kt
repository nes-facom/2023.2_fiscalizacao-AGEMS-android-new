package com.ufms.nes.features.template.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufms.nes.R
import com.ufms.nes.core.commons.Constants.EMPTY
import com.ufms.nes.core.ui.CardColor
import com.ufms.nes.core.ui.components.IconData
import com.ufms.nes.core.ui.components.SmallTopBar
import com.ufms.nes.core.ui.components.TopBarData
import com.ufms.nes.features.template.presentation.state.AddModelUiState
import com.ufms.nes.features.template.presentation.state.QuestionModelUiState
import com.ufms.nes.features.template.presentation.viewmodel.AddModelViewModel
import com.ufms.nes.features.template.ui.NesDialog

enum class EventQuestionType {
    ADD, EDIT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditModelScreen(
    onBackClick: () -> Unit,
    onEditAddQuestionClick: (EventQuestionType) -> Unit,
    viewModel: AddModelViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            SmallTopBar(
                data = TopBarData(title = R.string.create_model,
                    navigationIcon = Icons.Default.ArrowBack,
                    iconsActions = listOf(
                        IconData(
                            actionIcon = Icons.Default.Check,
                            onActionClick = {
                                viewModel.saveModelInDatabase()
                            },
                            isVisible = true,
                            actionIconContentDescription = R.string.save_model
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
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onEditAddQuestionClick(EventQuestionType.ADD)
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                text = { Text(stringResource(id = R.string.add_question), color = Color.White) },
                icon = {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            )
        }
    ) { paddingValues ->
        val uiState = viewModel.modelUiState.collectAsStateWithLifecycle()

        AddEditModelContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            viewModel = viewModel,
            onEditAddQuestionClick = onEditAddQuestionClick,
        )

        LaunchedEffect(uiState.value.modelSaved) {
            if (uiState.value.modelSaved) {
                onBackClick()
            }
        }
    }
}

@Composable
fun AddEditModelContent(
    modifier: Modifier = Modifier,
    uiState: State<AddModelUiState>,
    onEditAddQuestionClick: (EventQuestionType) -> Unit,
    viewModel: AddModelViewModel
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            value = uiState.value.name,
            onValueChange = { viewModel.enteredModelName(it) },
            placeholder = { Text(text = stringResource(id = R.string.name_model)) },
            supportingText = {
                if (uiState.value.nameIsEmpty) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Campo obrigatório",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = uiState.value.nameIsEmpty
        )

        Spacer(modifier = Modifier.height(15.dp))

        if (uiState.value.questionsIsEmpty) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                text = "Adicione ao menos uma questão!",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        uiState.value.questions.forEachIndexed { index, questionModel ->
            QuestionRow(
                question = questionModel,
                onEditQuestion = {
                    viewModel.loadQuestion(index).apply {
                        onEditAddQuestionClick(EventQuestionType.EDIT)
                    }
                },
                onDeleteQuestion = {
                    viewModel.removeQuestion(index)
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun QuestionRow(
    question: QuestionModelUiState,
    onEditQuestion: (QuestionModelUiState) -> Unit,
    onDeleteQuestion: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        NesDialog(
            onDismissRequest = {
                if (it) {
                    onDeleteQuestion()
                }
                openDialog.value = false
            },
            titleRes = R.string.remove_question,
            bodyRes = R.string.remove_question_body,
            confirmButtonRes = R.string.remove,
            dismissButtonRes = R.string.cancel
        )
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        shadowElevation = 5.dp,
        color = CardColor
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = question.question,
                fontSize = 18.sp
            )

            if (question.isObjective) {
                ExposedDropdown(
                    expanded = expanded,
                    onExpanded = { expanded = it },
                    items = question.responses
                )
            } else {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp, start = 10.dp),
                    text = stringResource(R.string.response),
                    fontSize = 15.sp,
                    color = Color.Gray
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEditQuestion(question) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { openDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdown(
    expanded: Boolean,
    onExpanded: (Boolean) -> Unit,
    items: List<String>,
) {

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        expanded = expanded,
        onExpandedChange = {
            onExpanded(!expanded)
        },
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = if (items.isEmpty()) EMPTY else items[0],
            readOnly = true,
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray
            ),
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
            items.forEach { response ->
                Text(text = response)
            }
        }
    }
}
