package com.sharipov.mynotificationmanager.core.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sharipov.mynotificationmanager.core.navigation.routes.Routes
import com.sharipov.mynotificationmanager.core.navigation.sharedViewModel.sharedViewModel
import com.sharipov.mynotificationmanager.ui.settings.SettingsScreen
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel

fun NavGraphBuilder.settingsGraph(navController: NavHostController) {
    navigation(
        startDestination = Routes.SettingsRoute.route,
        route = "settings_graph"
    ) {

        composable(Routes.SettingsRoute.route) {
            val viewModel = it.sharedViewModel<HomeViewModel>(navController)
            val settingsViewModel = it.sharedViewModel<SettingsViewModel>(navController)
            SettingsScreen(viewModel, settingsViewModel, navController)
        }

    }
}