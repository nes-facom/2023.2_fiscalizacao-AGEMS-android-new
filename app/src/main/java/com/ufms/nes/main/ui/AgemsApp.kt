package com.ufms.nes.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.navigation.compose.NavHost
import com.ufms.nes.R
import com.ufms.nes.core.ui.model.drawerOptions
import com.ufms.nes.features.authentication.presentation.loginNavigationRoute
import com.ufms.nes.features.registration.presentation.registrationNavigationRoute
import com.ufms.nes.main.navigation.NavRoutes
import com.ufms.nes.main.navigation.exitNavigationRoute
import com.ufms.nes.main.navigation.formNavigationRoute
import com.ufms.nes.main.navigation.homeNavigationRoute
import com.ufms.nes.main.navigation.mainGraph
import com.ufms.nes.main.navigation.modelNavigationRoute
import com.ufms.nes.main.navigation.navigateToForms
import com.ufms.nes.main.navigation.navigateToModels
import kotlinx.coroutines.launch

@Composable
fun AgemsApp(
    appState: AgemsAppState = rememberAgemsAppState()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf(drawerOptions[0]) }

    fun navigateHome() {
        appState.navController.navigate(NavRoutes.MainRoute.name) {
            popUpTo(NavRoutes.AuthenticationRoute.name) {
                inclusive = true
            }
        }
    }

    Surface {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet(
                    drawerShape = MaterialTheme.shapes.extraLarge,
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

                                    exitNavigationRoute -> {
                                        // TODO() - Deslogar usuário
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
                }
            },
            content = {
                NavHost(
                    navController = appState.navController,
                    startDestination = NavRoutes.AuthenticationRoute.name
                ) {
                    mainGraph(
                        drawerState = drawerState,
                        onBackClick = appState::onBackClick,
                        onLoginSuccess = {
                            navigateHome()
                        },
                        onRegistrationButtonClick = {
                            appState.navController.navigate(registrationNavigationRoute)
                        },
                        onShortcutClick = { route -> appState.navController.navigate(route) },
                        onReturnToLoginClick = {
                            appState.navController.navigate(loginNavigationRoute)
                        },
                        onRegistrationSuccess = {
                            navigateHome()
                        }
                    )
                }
            }
        )
    }
}
