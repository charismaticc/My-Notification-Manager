package com.sharipov.mynotificationmanager.core.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sharipov.mynotificationmanager.core.navigation.bottomNavBar.BottomNavigationBar
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.allNotificationsGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.applicationsGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.favoriteGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.notificationFilterGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.settingsGraph
import com.sharipov.mynotificationmanager.ui.PermissionScreen
import com.sharipov.mynotificationmanager.ui.allapplication.ApplicationsScreen
import com.sharipov.mynotificationmanager.ui.allnotifications.AllNotificationScreen
import com.sharipov.mynotificationmanager.ui.chat.ChatScreen
import com.sharipov.mynotificationmanager.ui.conversations.ConversationsScreen
import com.sharipov.mynotificationmanager.ui.favorite.FavoriteScreen
import com.sharipov.mynotificationmanager.ui.notificationmanagement.NotificationFilterScreen
import com.sharipov.mynotificationmanager.ui.settings.SettingsScreen
import com.sharipov.mynotificationmanager.ui.splashscreen.SplashScreen
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(
    navController: NavHostController,
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "all_notifications_graph",
        ) {
            favoriteGraph(navController = navController)
            applicationsGraph(navController = navController)
            allNotificationsGraph(navController = navController)
            notificationFilterGraph(navController = navController)
            settingsGraph(navController = navController)
        }
    }
}

//sealed class Screens(val route: String){
//    data object Splash: Screens(route = Constants.Screens.SPLASH_SCREEN)
//    data object Applications: Screens(route = Constants.Screens.APPLICATION_SCREEN)
//    data object Settings: Screens(route = Constants.Screens.SETTINGS_SCREEN)
//    data object Conversations: Screens(route = Constants.Screens.CONVERSATION_SCREEN)
//    data object Chat: Screens(route = Constants.Screens.CHAT_SCREEN)
//    data object AllNotifications: Screens(route = Constants.Screens.ALL_NOTIFICATIONS_SCREEN)
//    data object Favorite: Screens(route = Constants.Screens.FAVORITE_SCREEN)
//    data object Permissions: Screens(route = Constants.Screens.PERMISSIONS_SCREEN)
//    data object NotificationManagement: Screens(route = Constants.Screens.NOTIFICATION_MANAGEMENT_SCREEN)
//}

//@Composable
//fun SetupNavHost(
//    homeViewModel: HomeViewModel,
//    settingsViewModel: SettingsViewModel,
//    navController: NavHostController,
//) {
//    NavHost(
//        navController = navController,
//        startDestination = Screens.Splash.route
//    ) {
//
//        composable(route = Screens.Splash.route) {
//            SplashScreen(
//                navController = navController,
//                homeViewModel = homeViewModel,
//                settingsViewModel = settingsViewModel
//            )
//        }
//
//        composable(route = Screens.Permissions.route) {
//            PermissionScreen(navController = navController)
//        }
//
//        composable(route = Screens.Applications.route) {
//            ApplicationsScreen(homeViewModel = homeViewModel, navController = navController)
//        }
//
//        composable(route = Screens.AllNotifications.route) {
//            AllNotificationScreen(homeViewModel = homeViewModel, navController = navController)
//        }
//
//        composable(route = Screens.Conversations.route + "/{packageName}") { backStackEntry ->
//            ConversationsScreen(
//                homeViewModel = homeViewModel,
//                navController = navController,
//                packageName = backStackEntry.arguments?.getString("packageName") ?: ""
//            )
//        }
//
//        composable(route = Screens.Chat.route + "/{user}/{packageName}/{group}") { backStackEntry ->
//            ChatScreen(
//                homeViewModel = homeViewModel,
//                navController = navController,
//                userName =  backStackEntry.arguments?.getString("user") ?: "0",
//                packageName =  backStackEntry.arguments?.getString("packageName") ?: "",
//                group = backStackEntry.arguments?.getString("group") ?: "not_group",
//            )
//        }
//
//        composable(route = Screens.Settings.route) {
//            SettingsScreen(homeViewModel = homeViewModel, settingsViewModel = settingsViewModel, navController = navController)
//        }
//
//        composable(route = Screens.Favorite.route) {
//            FavoriteScreen(homeViewModel = homeViewModel, navController = navController)
//        }
//
//        composable(route = Screens.NotificationManagement.route) {
//            NotificationFilterScreen(
//                settingsViewModel = settingsViewModel,
//                navController = navController
//            )
//        }
//    }
//}