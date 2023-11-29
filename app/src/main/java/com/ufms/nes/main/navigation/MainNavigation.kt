package com.ufms.nes.main.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ufms.nes.domain.model.ConsumeUnit
import com.ufms.nes.domain.model.Model
import com.ufms.nes.features.consumeunit.screen.AddConsumeUnitScreen
import com.ufms.nes.features.consumeunit.screen.ConsumeUnitScreen
import com.ufms.nes.features.form.screen.FormsScreen
import com.ufms.nes.features.home.HomeScreen
import com.ufms.nes.features.synchronization.SynchronizationScreen
import com.ufms.nes.features.template.screen.ModelDetailsScreen
import com.ufms.nes.features.template.screen.ModelsScreen

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

fun NavController.navigateToSelectModel(navOptions: NavOptions? = null) {
    this.navigate(SELECT_MODEL_ROUTE, navOptions)
}

fun NavController.navigateToCreateModel(navOptions: NavOptions? = null) {
    this.navigate(CREATE_FORM_ROUTE, navOptions)
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
    onFloatingButtonClick: () -> Unit,
) {
    composable(route = formNavigationRoute) {
        FormsScreen(drawerState = drawerState, onFloatingButtonClick = onFloatingButtonClick)
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
const val SELECT_MODEL_ROUTE = "select_model_screen"
const val CREATE_FORM_ROUTE = "create_form_screen"
const val ADD_EDIT_MODEL_NAVIGATION_ROUTE = "add_edit_model_screen"
const val ADD_EDIT_QUESTION_NAVIGATION_ROUTE = "add_edit_question_screen"
const val MODEL_DETAIL_NAVIGATION_ROUTE = "model_detail_screen"

const val MODEL_ID_ARG = "modelId"