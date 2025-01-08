package com.sharipov.mynotificationmanager.core.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sharipov.mynotificationmanager.core.navigation.routes.Routes
import com.sharipov.mynotificationmanager.core.navigation.sharedViewModel.sharedViewModel
import com.sharipov.mynotificationmanager.ui.PermissionScreen
import com.sharipov.mynotificationmanager.ui.splashscreen.SplashScreen
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation(
        startDestination = Routes.SplashScreenRoute.route,
        route = "main_graph"
    ) {

        composable(Routes.SplashScreenRoute.route) {
            val settingsViewModel = it.sharedViewModel<SettingsViewModel>(navController)
            SplashScreen(navController = navController, settingsViewModel = settingsViewModel)
        }

        composable(Routes.PermissionsRoute.route) {
            PermissionScreen(navController)
        }

    }
}