package com.ufms.nes.features.authentication.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val loginNavigationRoute = "login_screen"

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit,
    onRegistrationButtonClick: () -> Unit
) {
    composable(route = loginNavigationRoute) {
        LoginScreen(onLoginSuccess = onLoginSuccess, onRegistrationButtonClick = onRegistrationButtonClick)
    }
}
