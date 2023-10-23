package com.ufms.nes.main.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ufms.nes.features.form.FormsScreen
import com.ufms.nes.features.home.HomeScreen
import com.ufms.nes.features.template.data.model.Model
import com.ufms.nes.features.template.presentation.ui.ModelDetailsScreen
import com.ufms.nes.features.template.presentation.ui.ModelsScreen

fun NavController.navigateToModels(navOptions: NavOptions? = null) {
    this.navigate(modelNavigationRoute, navOptions)
}

fun NavController.navigateToForms(navOptions: NavOptions? = null) {
    this.navigate(formNavigationRoute, navOptions)
}

fun NavController.navigateToAddEditQuestion(navOptions: NavOptions? = null) {
    this.navigate(ADD_EDIT_QUESTION_NAVIGATION_ROUTE, navOptions)
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

//fun NavGraphBuilder.addEditQuestionScreen(
//) {
//    composable(route = ADD_EDIT_QUESTION_NAVIGATION_ROUTE) {
//        AddQuestionScreen()
//    }
//}

//fun NavGraphBuilder.addEditModelScreen(
//    onBackClick: () -> Unit
//) {
//    composable(route = ADD_EDIT_MODEL_NAVIGATION_ROUTE) {
//        AddEditModelScreen(
//            onBackClick = onBackClick
//        )
//    }
//}

fun NavGraphBuilder.modelsScreen(
    drawerState: DrawerState,
    onFloatingButtonClick: () -> Unit,
    onModelClick: (Model) -> Unit
) {
    composable(route = modelNavigationRoute) {
        ModelsScreen(
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
        ModelDetailsScreen(onBack = onBackClick)
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
const val formNavigationRoute = "forms_screen"
const val ADD_EDIT_MODEL_NAVIGATION_ROUTE = "add_edit_model_screen"
const val ADD_EDIT_QUESTION_NAVIGATION_ROUTE = "add_edit_question_screen"
const val MODEL_DETAIL_NAVIGATION_ROUTE = "model_detail_screen"

const val MODEL_ID_ARG = "modelId"