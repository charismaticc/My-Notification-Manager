package com.sharipov.mynotificationmanager.core.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sharipov.mynotificationmanager.core.navigation.routes.Routes
import com.sharipov.mynotificationmanager.core.navigation.sharedViewModel.sharedViewModel
import com.sharipov.mynotificationmanager.ui.allapplication.ApplicationsScreen
import com.sharipov.mynotificationmanager.ui.chat.ChatScreen
import com.sharipov.mynotificationmanager.ui.conversations.ConversationsScreen
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel

fun NavGraphBuilder.applicationsGraph(navController: NavHostController) {
    navigation(
        startDestination = Routes.ApplicationsRoute.route,
        route = "applications_graph"
    ) {
        composable(Routes.ApplicationsRoute.route) {
            val viewModel = it.sharedViewModel<HomeViewModel>(navController)
            ApplicationsScreen(viewModel, navController)
        }

        composable(route = "conversations/{packageName}") { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<HomeViewModel>(navController)
            ConversationsScreen(
                homeViewModel = viewModel,
                navController = navController,
                packageName = backStackEntry.arguments?.getString("packageName") ?: ""
            )
        }

        composable(route = "chat/{user}/{packageName}/{group}") { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<HomeViewModel>(navController)
            ChatScreen(
                homeViewModel = viewModel,
                navController = navController,
                userName =  backStackEntry.arguments?.getString("user") ?: "0",
                packageName =  backStackEntry.arguments?.getString("packageName") ?: "",
                group = backStackEntry.arguments?.getString("group") ?: "not_group",
            )
        }
    }
}