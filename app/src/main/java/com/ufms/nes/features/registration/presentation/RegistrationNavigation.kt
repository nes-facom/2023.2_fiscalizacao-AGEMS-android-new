package com.ufms.nes.features.registration.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val registrationNavigationRoute = "registration_screen"

fun NavGraphBuilder.registrationScreen(
    onRegistrationSuccess: () -> Unit,
    onReturnToLoginClick: () -> Unit
) {
    composable(route = registrationNavigationRoute) {
        RegistrationScreen(onRegistrationSuccess = onRegistrationSuccess, onReturnToLoginClick = onReturnToLoginClick)
    }
}
