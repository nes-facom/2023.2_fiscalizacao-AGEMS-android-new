package com.ufms.nes.main.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.ufms.nes.features.authentication.presentation.loginNavigationRoute
import com.ufms.nes.features.authentication.presentation.loginScreen
import com.ufms.nes.features.home.homeNavigationRoute
import com.ufms.nes.features.home.homeScreen
import com.ufms.nes.features.registration.presentation.registrationNavigationRoute
import com.ufms.nes.features.registration.presentation.registrationScreen

enum class NavRoutes {
    MainRoute,
    AuthenticationRoute,
    RegistrationRoute
}

fun NavGraphBuilder.mainGraph(
    drawerState: DrawerState,
    onBackClick: () -> Unit,
    onFloatingButtonClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    onRegistrationButtonClick: () -> Unit,
    onRegistrationSuccess: () -> Unit
) {
    navigation(
        startDestination = loginNavigationRoute, route = NavRoutes.AuthenticationRoute.name
    ) {
        loginScreen(onLoginSuccess = onLoginSuccess, onRegistrationButtonClick = onRegistrationButtonClick)
    }
    navigation(startDestination = homeNavigationRoute, route = NavRoutes.MainRoute.name) {
        homeScreen(drawerState = drawerState)
    }
    navigation(startDestination = registrationNavigationRoute, route = NavRoutes.RegistrationRoute.name) {
        registrationScreen(onRegistrationSuccess = onRegistrationSuccess)
    }
}
