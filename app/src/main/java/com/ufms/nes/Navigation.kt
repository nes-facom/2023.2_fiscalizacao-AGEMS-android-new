package com.ufms.nes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ufms.nes.features.authentication.presentation.LoginScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
//            AgemsTypeScreen(modifier = Modifier.padding(16.dp))
            LoginScreen()
        }
        // TODO: Add more destinations
    }
}
