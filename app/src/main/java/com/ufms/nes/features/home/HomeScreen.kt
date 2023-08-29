package com.ufms.nes.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.DrawerTopBar
import com.ufms.nes.core.ui.components.ShortcutsCard
import com.ufms.nes.main.navigation.formNavigationRoute
import com.ufms.nes.main.navigation.modelNavigationRoute

@Composable
fun HomeScreen(
    drawerState: DrawerState,
    onShortcutClick: (route: String) -> Unit,
) {
    Scaffold(
        topBar = {
            DrawerTopBar(
                drawerState = drawerState,
                title = R.string.tab_home
            )
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            ShortcutsCard(
                modifier = Modifier.size(160.dp),
                icon = Icons.Default.Category,
                label = R.string.tab_model,
                onCardClickable = { onShortcutClick(modelNavigationRoute) }
            )

            ShortcutsCard(
                modifier = Modifier.size(160.dp),
                icon = Icons.Default.ListAlt,
                label = R.string.tab_form,
                onCardClickable = { onShortcutClick(formNavigationRoute) }
            )
        }
    }
}
