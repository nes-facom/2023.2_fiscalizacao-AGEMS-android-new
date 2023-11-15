package com.ufms.nes.features.template.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufms.nes.R
import com.ufms.nes.core.ui.CardColor
import com.ufms.nes.core.ui.components.SmallTopBar
import com.ufms.nes.core.ui.components.TopBarData
import com.ufms.nes.domain.model.Question
import com.ufms.nes.features.template.viewmodel.ModelDetailsUiState
import com.ufms.nes.features.template.viewmodel.ModelDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelDetailsScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: ModelDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.modelDetailsUiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SmallTopBar(
                data = TopBarData(title = R.string.details_model,
                    navigationIcon = Icons.Default.ArrowBack,
                    iconsActions = emptyList(),
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

        DetailsModelContent(
            modifier = modifier.padding(paddingValues),
            uiState = uiState,
        )
    }
}

@Composable
fun DetailsModelContent(
    modifier: Modifier = Modifier,
    uiState: State<ModelDetailsUiState>
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
            onValueChange = { },
            enabled = false
        )

        Spacer(modifier = Modifier.height(15.dp))

        uiState.value.questions.forEach { questionModel ->
            QuestionDetailRow(question = questionModel)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun QuestionDetailRow(
    question: Question
) {
    var expanded by remember { mutableStateOf(false) }

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
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = question.question.orEmpty(),
                fontSize = 18.sp
            )

            if (question.isObjective == true) {
                ExposedDropdown(
                    expanded = expanded,
                    onExpanded = { expanded = it },
                    items = question.responses.map {
                        it.description.orEmpty()
                    }
                )
            } else {
                Text(
                    modifier = Modifier
                        .padding(bottom = 5.dp, start = 10.dp)
                        .defaultMinSize(minHeight = 48.dp),
                    text = stringResource(R.string.response),
                    fontSize = 15.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp)
                )
            }
        }
    }
}
