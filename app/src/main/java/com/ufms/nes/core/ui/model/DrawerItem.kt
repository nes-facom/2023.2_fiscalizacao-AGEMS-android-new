package com.ufms.nes.core.ui.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.ufms.nes.R
import com.ufms.nes.features.home.homeNavigationRoute

data class DrawerItem(
    @StringRes val label: Int,
    val route: String,
    val icon: ImageVector
)

val drawerOptions = listOf(
    DrawerItem(
        icon = Icons.Default.Home,
        label = R.string.tab_home,
        route = homeNavigationRoute
    )
)
