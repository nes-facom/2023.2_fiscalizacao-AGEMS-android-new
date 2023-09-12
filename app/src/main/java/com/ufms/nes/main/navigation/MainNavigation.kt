package com.ufms.nes.main.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ufms.nes.features.form.FormsScreen
import com.ufms.nes.features.home.HomeScreen
import com.ufms.nes.features.models.presentation.ModelsScreen

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
