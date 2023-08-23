package com.ufms.nes.features.home

import androidx.compose.material3.DrawerState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val homeNavigationRoute = "home_screen"

fun NavGraphBuilder.homeScreen(
    drawerState: DrawerState,
) {
    composable(route = homeNavigationRoute) {
        HomeScreen(
            drawerState = drawerState
        )
    }
}
