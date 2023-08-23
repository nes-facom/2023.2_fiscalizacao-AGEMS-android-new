package com.ufms.nes.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.ufms.nes.R
import com.ufms.nes.core.ui.model.drawerOptions
import com.ufms.nes.features.home.homeNavigationRoute
import com.ufms.nes.main.navigation.NavRoutes
import com.ufms.nes.main.navigation.mainGraph
import kotlinx.coroutines.launch

@Composable
fun AgemsApp(
    appState: AgemsAppState = rememberAgemsAppState()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf(drawerOptions[0]) }

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
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.titleLarge
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
                        onFloatingButtonClick = {},
                        onLoginSuccess = {
                            appState.navController.navigate(NavRoutes.MainRoute.name) {
                                popUpTo(NavRoutes.AuthenticationRoute.name) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
        )
    }
}
