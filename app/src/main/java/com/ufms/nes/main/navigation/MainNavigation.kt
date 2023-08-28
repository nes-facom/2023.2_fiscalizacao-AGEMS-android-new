package com.ufms.nes.main.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.ufms.nes.features.authentication.presentation.loginNavigationRoute
import com.ufms.nes.features.authentication.presentation.loginScreen
import com.ufms.nes.features.home.homeNavigationRoute
import com.ufms.nes.features.home.homeScreen

enum class NavRoutes {
    MainRoute,
    AuthenticationRoute
}

fun NavGraphBuilder.mainGraph(
    drawerState: DrawerState,
    onBackClick: () -> Unit,
    onFloatingButtonClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    navigation(
        startDestination = loginNavigationRoute, route = NavRoutes.AuthenticationRoute.name
    ) {
        loginScreen(onLoginSuccess = onLoginSuccess)
    }
    navigation(startDestination = homeNavigationRoute, route = NavRoutes.MainRoute.name) {
        homeScreen(drawerState = drawerState)
    }
}
