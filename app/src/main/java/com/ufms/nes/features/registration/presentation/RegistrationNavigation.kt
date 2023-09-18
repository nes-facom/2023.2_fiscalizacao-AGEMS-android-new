package com.ufms.nes.features.registration.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REGISTRATION_NAVIGATION_ROUTE = "registration_screen"

fun NavGraphBuilder.registrationScreen(
    onRegistrationSuccess: () -> Unit,
    onReturnToLoginClick: () -> Unit
) {
    composable(route = REGISTRATION_NAVIGATION_ROUTE) {
        RegistrationScreen(onRegistrationSuccess = onRegistrationSuccess, onReturnToLoginClick = onReturnToLoginClick)
    }
}
