package com.sharipov.mynotificationmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sharipov.mynotificationmanager.ui.allnotifications.AllNotificationScreen
import com.sharipov.mynotificationmanager.ui.conversations.ConversationsScreen
import com.sharipov.mynotificationmanager.ui.details.DetailsScreen
import com.sharipov.mynotificationmanager.ui.favorite.FavoriteScreen
import com.sharipov.mynotificationmanager.ui.allapplivation.ApplicationsScreen
import com.sharipov.mynotificationmanager.ui.chat.ChatScreen
import com.sharipov.mynotificationmanager.ui.settings.SettingsScreen
import com.sharipov.mynotificationmanager.ui.splashscreen.SplashScreen
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel


sealed class Screens(val route: String){
    object Splash: Screens(route = Constants.Screens.SPLASH_SCREEN)
    object Applications: Screens(route = Constants.Screens.APPLICATION_SCREEN)
    object Settings: Screens(route = Constants.Screens.SETTINGS_SCREEN)
    object Conversations: Screens(route = Constants.Screens.CONVERSATION_SCREEN)
    object Chat: Screens(route = Constants.Screens.CHAT_SCREEN)
    object Details: Screens(route = Constants.Screens.DETAILS_SCREEN)
    object AllNotifications: Screens(route = Constants.Screens.ALL_NOTIFICATIONS_SCREEN)
    object Favorite: Screens(route = Constants.Screens.FAVORITE_SCREEN)
}
@Composable
fun SetupNavHost(
    homeViewModel: HomeViewModel,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screens.Applications.route) {
            ApplicationsScreen(homeViewModel = homeViewModel, navController = navController)
        }

        composable(route = Screens.AllNotifications.route) {
            AllNotificationScreen(homeViewModel = homeViewModel, navController = navController)
        }

        composable(route = Screens.Conversations.route + "/{packageName}") { backStackEntry ->
            ConversationsScreen(
                homeViewModel = homeViewModel,
                navController = navController,
                packageName = backStackEntry.arguments?.getString("packageName") ?: ""
            )
        }

        composable(route = Screens.Details.route + "/{Id}") { backStackEntry ->
            DetailsScreen(
                homeViewModel = homeViewModel,
                notificationId = backStackEntry.arguments?.getString("Id") ?: "0"
            )
        }

        composable(route = Screens.Chat.route + "/{user}/{packageName}") { backStackEntry ->
            ChatScreen(
                homeViewModel = homeViewModel,
                navController = navController,
                userName =  backStackEntry.arguments?.getString("user") ?: "0",
                packageName =  backStackEntry.arguments?.getString("packageName") ?: ""
            )
        }

        composable(route = Screens.Settings.route) {
            SettingsScreen(homeViewModel = homeViewModel, navController = navController)
        }

        composable(route = Screens.Favorite.route) {
            FavoriteScreen(homeViewModel = homeViewModel, navController = navController)
        }
    }
}