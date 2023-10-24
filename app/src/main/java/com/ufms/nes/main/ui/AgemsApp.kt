package com.ufms.nes.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ufms.nes.R
import com.ufms.nes.core.ui.ContainerColor
import com.ufms.nes.core.ui.model.drawerOptions
import com.ufms.nes.features.authentication.presentation.loginNavigationRoute
import com.ufms.nes.features.authentication.presentation.loginScreen
import com.ufms.nes.features.template.presentation.ui.AddEditModelScreen
import com.ufms.nes.features.template.presentation.ui.AddQuestionScreen
import com.ufms.nes.features.template.presentation.viewmodel.AddModelViewModel
import com.ufms.nes.features.template.ui.NesDialog
import com.ufms.nes.main.navigation.ADD_EDIT_MODEL_NAVIGATION_ROUTE
import com.ufms.nes.main.navigation.ADD_EDIT_QUESTION_NAVIGATION_ROUTE
import com.ufms.nes.main.navigation.NavRoutes
import com.ufms.nes.main.navigation.formNavigationRoute
import com.ufms.nes.main.navigation.formsScreen
import com.ufms.nes.main.navigation.homeNavigationRoute
import com.ufms.nes.main.navigation.homeScreen
import com.ufms.nes.main.navigation.modelDetailScreen
import com.ufms.nes.main.navigation.modelNavigationRoute
import com.ufms.nes.main.navigation.modelsScreen
import com.ufms.nes.main.navigation.navigateToAddEditQuestion
import com.ufms.nes.main.navigation.navigateToForms
import com.ufms.nes.main.navigation.navigateToModelDetail
import com.ufms.nes.main.navigation.navigateToModels
import kotlinx.coroutines.launch

@Composable
fun AgemsApp(
    appState: AgemsAppState = rememberAgemsAppState(),
    windowSize: WindowSizeClass,
    deleteUserPreferences: () -> Unit,
    userLogged: Boolean
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf(drawerOptions[0]) }
    val openDialog = remember { mutableStateOf(false) }

    val customModifier = if (windowSize.widthSizeClass > WindowWidthSizeClass.Compact) {
        Modifier.padding(horizontal = 65.dp, vertical = 25.dp)
    } else {
        Modifier
    }

    if (openDialog.value) {
        NesDialog(
            onDismissRequest = {
                if (it) {
                    deleteUserPreferences()
                    openDialog.value = false
                    appState.navController.navigate(NavRoutes.AuthenticationRoute.name)
                } else {
                    openDialog.value = false
                }
            },
            titleRes = R.string.tab_exit,
            bodyRes = R.string.confirm_exit,
            confirmButtonRes = R.string.confirm,
            dismissButtonRes = R.string.cancel
        )
    }

    Surface {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.width(280.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                            .padding(start = 24.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            alignment = Alignment.BottomStart,
                            painter = painterResource(id = R.drawable.app_logo),
                            contentDescription = stringResource(R.string.logo_content_description)
                        )
                    }
                    Divider(thickness = 1.dp, modifier = Modifier.padding(bottom = 10.dp))
                    drawerOptions.forEach { item ->
                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = ContainerColor
                            ),
                            shape = MaterialTheme.shapes.medium,
                            label = { Text(text = stringResource(id = item.label)) },
                            selected = item == selectedItem,
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedItem = item
                                when (item.route) {
                                    homeNavigationRoute -> {
                                        appState.navController.navigate(item.route) {
                                            popUpTo(NavRoutes.MainRoute.name)
                                        }
                                    }

                                    modelNavigationRoute -> {
                                        appState.navController.navigateToModels()
                                    }

                                    formNavigationRoute -> {
                                        appState.navController.navigateToForms()
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = stringResource(id = item.label)
                                )
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(id = R.string.tab_exit)) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            openDialog.value = true
                        },
                        selected = false,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = stringResource(id = R.string.tab_exit)
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            },
            content = {
                NavHost(
                    navController = appState.navController,
                    startDestination = if (userLogged) {
                        NavRoutes.MainRoute.name
                    } else {
                        NavRoutes.AuthenticationRoute.name
                    }
                ) {
                    navigation(
                        startDestination = loginNavigationRoute,
                        route = NavRoutes.AuthenticationRoute.name
                    ) {
                        loginScreen(onLoginSuccess = {
                            appState.navController.navigate(NavRoutes.MainRoute.name) {
                                popUpTo(NavRoutes.AuthenticationRoute.name) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                    navigation(
                        startDestination = homeNavigationRoute,
                        route = NavRoutes.MainRoute.name
                    ) {
                        homeScreen(
                            drawerState = drawerState,
                            onShortcutClick = { route ->
                                appState.navController.navigate(route)
                            }
                        )
                        modelsScreen(
                            modifier = customModifier,
                            drawerState = drawerState,
                            onFloatingButtonClick = {
                                appState.navController.navigate(NavRoutes.ModelRoute.name)
                            },
                            onModelClick = {
                                appState.navController.navigateToModelDetail(modelId = it.id.toString())
                            }
                        )
                        formsScreen(drawerState = drawerState)
                        modelDetailScreen(
                            modifier = customModifier,
                            onBackClick = { appState.onBackClick() }
                        )
                    }

                    navigation(
                        startDestination = ADD_EDIT_MODEL_NAVIGATION_ROUTE,
                        route = NavRoutes.ModelRoute.name
                    ) {
                        composable(route = ADD_EDIT_MODEL_NAVIGATION_ROUTE) {
                            val viewModel =
                                it.sharedViewModel<AddModelViewModel>(appState.navController)
                            AddEditModelScreen(
                                modifier = customModifier,
                                onBackClick = appState::onBackClick,
                                viewModel = viewModel,
                                onEditAddQuestionClick = {
                                    appState.navController.navigateToAddEditQuestion()
                                }
                            )
                        }
                        composable(route = ADD_EDIT_QUESTION_NAVIGATION_ROUTE) {
                            val viewModel =
                                it.sharedViewModel<AddModelViewModel>(appState.navController)
                            AddQuestionScreen(
                                modifier = customModifier,
                                viewModel = viewModel,
                                onBack = { appState.onBackClick() }
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}