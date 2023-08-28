package com.ufms.nes.features.models

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.DrawerTopBar

@Composable
fun ModelsScreen(
    drawerState: DrawerState,
) {
    Scaffold(
        topBar = {
            DrawerTopBar(
                drawerState = drawerState,
                title = R.string.tab_model
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Model Screen")
        }
    }
}
