package com.sharipov.mynotificationmanager.core.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sharipov.mynotificationmanager.core.navigation.routes.Routes
import com.sharipov.mynotificationmanager.core.navigation.sharedViewModel.sharedViewModel
import com.sharipov.mynotificationmanager.ui.favorite.FavoriteScreen
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel

fun NavGraphBuilder.favoriteGraph(navController: NavHostController) {
    navigation(
        startDestination = Routes.FavoriteRoute.route,
        route = "favorite_graph"
    ) {

        composable(Routes.FavoriteRoute.route) {
            val viewModel = it.sharedViewModel<HomeViewModel>(navController)
            FavoriteScreen(viewModel, navController)
        }
    }
}