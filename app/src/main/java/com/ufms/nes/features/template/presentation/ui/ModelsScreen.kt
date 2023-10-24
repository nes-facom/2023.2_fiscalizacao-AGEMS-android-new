package com.ufms.nes.features.template.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.DrawerTopBar
import com.ufms.nes.core.ui.components.ModelItem
import com.ufms.nes.features.template.data.model.Model
import com.ufms.nes.features.template.presentation.viewmodel.ModelsViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModelsScreen(
    modifier: Modifier,
    drawerState: DrawerState,
    onFloatingButtonClick: () -> Unit,
    onModelClick: (Model) -> Unit,
    modelsViewModel: ModelsViewModel = hiltViewModel()
) {
    val uiState by modelsViewModel.uiState.collectAsStateWithLifecycle()

    val refreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = modelsViewModel::fetchModels
    )

    Scaffold(
        topBar = {
            DrawerTopBar(drawerState = drawerState, title = R.string.tab_model)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFloatingButtonClick() },
                backgroundColor = MaterialTheme.colorScheme.primary,
                content = {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
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
                    .padding(24.dp)
                    .pullRefresh(refreshState),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(uiState.models) {
                    ModelItem(label = it.name ?: "", onModelClick = { onModelClick(it) })
                }
            }

            PullRefreshIndicator(
                refreshing = uiState.isLoading, state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
