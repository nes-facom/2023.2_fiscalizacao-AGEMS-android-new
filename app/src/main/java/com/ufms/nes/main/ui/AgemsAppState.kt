package com.ufms.nes.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAgemsAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): AgemsAppState {

    return remember(navController, coroutineScope) {
        AgemsAppState(navController)
    }
}

@Stable
class AgemsAppState(
    val navController: NavHostController
) {

    fun onBackClick() {
        navController.popBackStack()
    }
}
