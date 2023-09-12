package com.ufms.nes.features.models.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.DrawerTopBar
import com.ufms.nes.core.ui.components.ModelItem

@Composable
fun ModelsScreen(
    drawerState: DrawerState,
    modelsViewModel: ModelsViewModel = hiltViewModel()
) {

    val uiState by modelsViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            DrawerTopBar(
                drawerState = drawerState,
                title = R.string.tab_model
            )
        }
    ) { paddingValues ->


        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(uiState.models) {
                ModelItem(label = it.name, onModelClick = {})
            }
        }

    }
}
