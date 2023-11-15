package com.ufms.nes.features.authentication

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val loginNavigationRoute = "login_screen"

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit
) {
    composable(route = loginNavigationRoute) {
        LoginScreen(onLoginSuccess = onLoginSuccess)
    }
}
