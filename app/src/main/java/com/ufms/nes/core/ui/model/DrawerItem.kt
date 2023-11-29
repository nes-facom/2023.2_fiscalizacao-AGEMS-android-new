package com.ufms.nes.core.ui.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Sync
import androidx.compose.ui.graphics.vector.ImageVector
import com.ufms.nes.R
import com.ufms.nes.main.navigation.consumeUnitRoute
import com.ufms.nes.main.navigation.formNavigationRoute
import com.ufms.nes.main.navigation.homeNavigationRoute
import com.ufms.nes.main.navigation.modelNavigationRoute
import com.ufms.nes.main.navigation.synchronizationRoute

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
    ),
    DrawerItem(
        icon = Icons.Default.Category,
        label = R.string.tab_model,
        route = modelNavigationRoute
    ),
    DrawerItem(
        icon = Icons.Default.ListAlt,
        label = R.string.tab_form,
        route = formNavigationRoute
    ),
    DrawerItem(
        icon = Icons.Default.LocationOn,
        label = R.string.tab_unit,
        route = consumeUnitRoute
    ),
    DrawerItem(
        icon = Icons.Default.Sync,
        label = R.string.synchronization,
        route = synchronizationRoute
    )
)
