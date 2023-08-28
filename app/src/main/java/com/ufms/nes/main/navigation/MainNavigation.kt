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
        modelsScreen(drawerState = drawerState)
        formsScreen(drawerState = drawerState)
    }
}

const val homeNavigationRoute = "home_screen"
const val modelNavigationRoute = "models_screen"
const val formNavigationRoute = "forms_screen"

fun NavGraphBuilder.homeScreen(
    drawerState: DrawerState,
) {
    composable(route = homeNavigationRoute) {
        HomeScreen(
            drawerState = drawerState
        )
    }
}

fun NavController.navigateToModels(navOptions: NavOptions? = null) {
    this.navigate(modelNavigationRoute, navOptions)
}

fun NavGraphBuilder.modelsScreen(
    drawerState: DrawerState,
) {
    composable(route = modelNavigationRoute) {
        ModelsScreen(
            drawerState = drawerState
        )
    }
}

fun NavController.navigateToForms(navOptions: NavOptions? = null) {
    this.navigate(formNavigationRoute, navOptions)
}

fun NavGraphBuilder.formsScreen(
    drawerState: DrawerState,
) {
    composable(route = formNavigationRoute) {
        FormsScreen(
            drawerState = drawerState
        )
    }
}
