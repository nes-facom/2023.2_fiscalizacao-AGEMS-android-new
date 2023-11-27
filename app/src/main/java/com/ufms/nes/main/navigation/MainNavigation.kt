package com.ufms.nes.main.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.ufms.nes.domain.model.ConsumeUnit
import com.ufms.nes.domain.model.Model
import com.ufms.nes.features.consumeunit.presentation.AddConsumeUnitScreen
import com.ufms.nes.features.consumeunit.presentation.ConsumeUnitScreen
import com.ufms.nes.features.authentication.presentation.loginNavigationRoute
import com.ufms.nes.features.authentication.presentation.loginScreen
import com.ufms.nes.features.form.FormsScreen
import com.ufms.nes.features.home.HomeScreen
import com.ufms.nes.features.registration.presentation.registrationScreen
import com.ufms.nes.features.synchronization.SynchronizationScreen
import com.ufms.nes.features.template.presentation.ui.ModelDetailsScreen
import com.ufms.nes.features.template.presentation.ui.ModelsScreen

fun NavGraphBuilder.modelsScreen(
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
        formsScreen(drawerState = drawerState)
    }
}

fun NavController.navigateToModels(navOptions: NavOptions? = null) {
    this.navigate(modelNavigationRoute, navOptions)
}

fun NavController.navigateToSynchronization(navOptions: NavOptions? = null) {
    this.navigate(synchronizationRoute, navOptions)
}

fun NavController.navigateToConsumeUnit(navOptions: NavOptions? = null) {
    this.navigate(consumeUnitRoute, navOptions)
}

fun NavController.navigateToForms(navOptions: NavOptions? = null) {
    this.navigate(formNavigationRoute, navOptions)
}

fun NavController.navigateToAddEditQuestion(navOptions: NavOptions? = null) {
    this.navigate(ADD_EDIT_QUESTION_NAVIGATION_ROUTE, navOptions)
}

fun NavController.navigateToAddConsumeUnit(navOptions: NavOptions? = null) {
    this.navigate(addConsumeUnitRoute, navOptions)
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

fun NavGraphBuilder.synchronizationScreen(
    drawerState: DrawerState,
) {
    composable(route = synchronizationRoute) {
        SynchronizationScreen(drawerState = drawerState)
    }
}

fun NavGraphBuilder.consumeUnitScreen(
    modifier: Modifier,
    drawerState: DrawerState,
    onFloatingButtonClick: () -> Unit,
    onConsumeUnitClick: (ConsumeUnit) -> Unit
) {
    composable(route = consumeUnitRoute) {
        ConsumeUnitScreen(
            modifier = modifier,
            drawerState = drawerState,
            onFloatingButtonClick = onFloatingButtonClick,
            onConsumeUnitClick = onConsumeUnitClick
        )
    }
}

fun NavGraphBuilder.addConsumeUnitScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    composable(route = addConsumeUnitRoute) {
        AddConsumeUnitScreen(
            modifier = modifier,
            onBack = onBack
        )
    }
}

fun NavGraphBuilder.modelsScreen(
    modifier: Modifier,
    drawerState: DrawerState,
    onFloatingButtonClick: () -> Unit,
    onModelClick: (Model) -> Unit
) {
    composable(route = modelNavigationRoute) {
        ModelsScreen(
            modifier = modifier,
            drawerState = drawerState,
            onFloatingButtonClick = onFloatingButtonClick,
            onModelClick = onModelClick
        )
    }
}

fun NavGraphBuilder.formsScreen(
    drawerState: DrawerState,
) {
    composable(route = formNavigationRoute) {
        FormsScreen(drawerState = drawerState)
    }
}

fun NavGraphBuilder.modelDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    composable(
        route = "$MODEL_DETAIL_NAVIGATION_ROUTE?$MODEL_ID_ARG={$MODEL_ID_ARG}",
        arguments = listOf(
            navArgument(MODEL_ID_ARG) {
                type = NavType.StringType; nullable = true
            },
        )
    ) {
        ModelDetailsScreen(modifier = modifier, onBack = onBackClick)
    }
}

fun NavController.navigateToModelDetail(
    navOptions: NavOptions? = null,
    modelId: String?
) {
    this.navigate("$MODEL_DETAIL_NAVIGATION_ROUTE?$MODEL_ID_ARG=${modelId}", navOptions)
}

enum class NavRoutes {
    MainRoute,
    AuthenticationRoute,
    ModelRoute
}

const val homeNavigationRoute = "home_screen"
const val modelNavigationRoute = "models_screen"
const val synchronizationRoute = "synchronization_screen"
const val consumeUnitRoute = "consume_unit_screen"
const val addConsumeUnitRoute = "add_consume_unit_screen"
const val formNavigationRoute = "forms_screen"
const val ADD_EDIT_MODEL_NAVIGATION_ROUTE = "add_edit_model_screen"
const val ADD_EDIT_QUESTION_NAVIGATION_ROUTE = "add_edit_question_screen"
const val MODEL_DETAIL_NAVIGATION_ROUTE = "model_detail_screen"

const val MODEL_ID_ARG = "modelId"