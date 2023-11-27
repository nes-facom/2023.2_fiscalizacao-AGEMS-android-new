package com.ufms.nes.features.synchronization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.DrawerTopBar

@Composable
fun SynchronizationScreen(
    drawerState: DrawerState,
    viewModel: SynchronizationViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            DrawerTopBar(
                drawerState = drawerState,
                title = R.string.synchronization
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
        ) {

            Button(modifier = Modifier.fillMaxWidth(), onClick = { viewModel.getAll() }) {
                Text(text = stringResource(R.string.download_data))
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.sendAll() },
            ) {
                Text(text = stringResource(R.string.send_data))
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.syncAll() },
            ) {
                Text(text = stringResource(R.string.sync_all))
            }
        }
    }
}
