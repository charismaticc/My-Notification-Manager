package com.sharipov.mynotificationmanager.core.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sharipov.mynotificationmanager.core.navigation.routes.Routes
import com.sharipov.mynotificationmanager.core.navigation.sharedViewModel.sharedViewModel
import com.sharipov.mynotificationmanager.ui.notificationmanagement.NotificationFilterScreen
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel

fun NavGraphBuilder.notificationFilterGraph(navController: NavHostController) {
    navigation(
        startDestination = Routes.NotificationFilterRoute.route,
        route = "notification_filter_graph"
    ) {

        composable(Routes.NotificationFilterRoute.route) {
            val viewModel = it.sharedViewModel<SettingsViewModel>(navController)
            NotificationFilterScreen(viewModel, navController)
        }
    }
}