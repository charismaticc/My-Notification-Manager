package com.sharipov.mynotificationmanager.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sharipov.mynotificationmanager.ui.allnotifications.AllNotificationScreen
import com.sharipov.mynotificationmanager.ui.conversations.ConversationsScreen
import com.sharipov.mynotificationmanager.ui.favorite.FavoriteScreen
import com.sharipov.mynotificationmanager.ui.allapplication.ApplicationsScreen
import com.sharipov.mynotificationmanager.ui.chat.ChatScreen
import com.sharipov.mynotificationmanager.ui.notificationmanagement.NotificationManagement
import com.sharipov.mynotificationmanager.ui.settings.SettingsScreen
import com.sharipov.mynotificationmanager.ui.splashscreen.SplashScreen
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel


sealed class Screens(val route: String){
    object Splash: Screens(route = Constants.Screens.SPLASH_SCREEN)
    object Applications: Screens(route = Constants.Screens.APPLICATION_SCREEN)
    object Settings: Screens(route = Constants.Screens.SETTINGS_SCREEN)
    object Conversations: Screens(route = Constants.Screens.CONVERSATION_SCREEN)
    object Chat: Screens(route = Constants.Screens.CHAT_SCREEN)
    object AllNotifications: Screens(route = Constants.Screens.ALL_NOTIFICATIONS_SCREEN)
    object Favorite: Screens(route = Constants.Screens.FAVORITE_SCREEN)
    object NotificationManagement: Screens(route = Constants.Screens.NOTIFICATION_MANAGEMENT_SCREEN)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavHost(
    homeViewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {

        composable(route = Screens.Splash.route) {
            SplashScreen(
                navController = navController,
                homeViewModel = homeViewModel,
                settingsViewModel = settingsViewModel
            )
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

        composable(route = Screens.Chat.route + "/{user}/{packageName}/{group}") { backStackEntry ->
            ChatScreen(
                homeViewModel = homeViewModel,
                navController = navController,
                userName =  backStackEntry.arguments?.getString("user") ?: "0",
                packageName =  backStackEntry.arguments?.getString("packageName") ?: "",
                group = backStackEntry.arguments?.getString("group") ?: "not_group",
            )
        }

        composable(route = Screens.Settings.route) {
            SettingsScreen(settingsViewModel = settingsViewModel, navController = navController)
        }

        composable(route = Screens.Favorite.route) {
            FavoriteScreen(homeViewModel = homeViewModel, navController = navController)
        }

        composable(route = Screens.NotificationManagement.route) {
            NotificationManagement(
                settingsViewModel = settingsViewModel,
                navController = navController
            )
        }
    }
}