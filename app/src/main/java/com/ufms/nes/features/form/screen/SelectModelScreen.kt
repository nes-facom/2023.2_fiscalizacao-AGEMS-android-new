package com.ufms.nes.features.form.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.ModelItem
import com.ufms.nes.core.ui.components.SmallTopBar
import com.ufms.nes.core.ui.components.TopBarData
import com.ufms.nes.features.form.viewmodel.CreateFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectModelScreen(
    modifier: Modifier,
    onBack: () -> Unit,
    onModelClick: () -> Unit,
    modelsViewModel: CreateFormViewModel
) {
    val models by modelsViewModel.models.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SmallTopBar(
                data = TopBarData(title = R.string.select_model,
                    navigationIcon = Icons.Default.ArrowBack,
                    iconsActions = listOf(),
                    onNavigationClick = {
                        modelsViewModel.clearCurrentModel().apply {
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

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(models) {
                    ModelItem(label = it.name, onModelClick = {

                        it.id?.let { modelId ->
                            modelsViewModel.selectModel(modelId)
                            onModelClick()
                        }

                    })
                }
            }
        }
    }
}
