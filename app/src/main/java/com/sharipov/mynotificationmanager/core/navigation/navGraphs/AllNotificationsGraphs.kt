package com.sharipov.mynotificationmanager.core.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sharipov.mynotificationmanager.core.navigation.routes.Routes
import com.sharipov.mynotificationmanager.core.navigation.sharedViewModel.sharedViewModel
import com.sharipov.mynotificationmanager.ui.allnotifications.AllNotificationScreen
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel

fun NavGraphBuilder.allNotificationsGraph(navController: NavHostController) {
    navigation(
        startDestination = Routes.AllNotificationRoute.route,
        route = "all_notifications_graph"
    ) {

        composable(Routes.AllNotificationRoute.route) {
            val viewModel = it.sharedViewModel<HomeViewModel>(navController)
            AllNotificationScreen(viewModel, navController)
        }

    }
}