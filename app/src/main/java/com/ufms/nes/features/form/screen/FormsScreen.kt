package com.ufms.nes.features.form.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.DrawerTopBar

@Composable
fun FormsScreen(
    drawerState: DrawerState,
    onFloatingButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            DrawerTopBar(
                drawerState = drawerState,
                title = R.string.tab_form
            )
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Formulário")
        }
    }
}
