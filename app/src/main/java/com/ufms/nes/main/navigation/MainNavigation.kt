package com.ufms.nes.main.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ufms.nes.features.authentication.presentation.loginNavigationRoute
import com.ufms.nes.features.authentication.presentation.loginScreen
import com.ufms.nes.features.form.FormsScreen
import com.ufms.nes.features.home.HomeScreen
import com.ufms.nes.features.models.ModelsScreen
import com.ufms.nes.features.registration.presentation.registrationNavigationRoute
import com.ufms.nes.features.registration.presentation.registrationScreen

fun NavGraphBuilder.mainGraph(
    drawerState: DrawerState,
    onBackClick: () -> Unit,
    onShortcutClick: (route: String) -> Unit,
    onRegistrationButtonClick: () -> Unit,
    onReturnToLoginClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    onRegistrationSuccess: () -> Unit
) {
    navigation(
        startDestination = loginNavigationRoute, route = NavRoutes.AuthenticationRoute.name
    ) {
        loginScreen(onLoginSuccess = onLoginSuccess, onRegistrationButtonClick = onRegistrationButtonClick)
        registrationScreen(onRegistrationSuccess = onRegistrationSuccess, onReturnToLoginClick = onReturnToLoginClick)
    }
    navigation(startDestination = homeNavigationRoute, route = NavRoutes.MainRoute.name) {
        homeScreen(drawerState = drawerState, onShortcutClick = onShortcutClick)
        modelsScreen(drawerState = drawerState)
        formsScreen(drawerState = drawerState)
    }
}

fun NavController.navigateToModels(navOptions: NavOptions? = null) {
    this.navigate(modelNavigationRoute, navOptions)
}

fun NavController.navigateToForms(navOptions: NavOptions? = null) {
    this.navigate(formNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    drawerState: DrawerState,
    onShortcutClick: (route: String) -> Unit,
) {
    composable(route = homeNavigationRoute) {
        HomeScreen(
            drawerState = drawerState,
            onShortcutClick = onShortcutClick
        )
    }
}

fun NavGraphBuilder.modelsScreen(
    drawerState: DrawerState,
) {
    composable(route = modelNavigationRoute) {
        ModelsScreen(drawerState = drawerState)
    }
}

fun NavGraphBuilder.formsScreen(
    drawerState: DrawerState,
) {
    composable(route = formNavigationRoute) {
        FormsScreen(drawerState = drawerState)
    }
}

enum class NavRoutes {
    MainRoute,
    AuthenticationRoute
}

const val homeNavigationRoute = "home_screen"
const val modelNavigationRoute = "models_screen"
const val formNavigationRoute = "forms_screen"
const val exitNavigationRoute = "exit_app"
