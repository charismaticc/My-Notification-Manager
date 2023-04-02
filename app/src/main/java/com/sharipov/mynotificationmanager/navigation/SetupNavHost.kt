package com.sharipov.mynotificationmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sharipov.mynotificationmanager.ui.home.HomeScreen
import com.sharipov.mynotificationmanager.ui.settings.SettingsScreen
import com.sharipov.mynotificationmanager.ui.splashscreen.SplashScreen
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel


sealed class Screens(val route: String){
    object Splash: Screens(route = Constants.Screens.SPLASH_SCREEN)
    object Main: Screens(route = Constants.Screens.MAIN_SCREEN)
    object Details: Screens(route = Constants.Screens.SETTINGS_SCREEN)
}

@Composable
fun SetupNavHost(homeViewModel: HomeViewModel, navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ){
        composable(route = Screens.Splash.route){
            SplashScreen(navController = navController)
        }

        composable(route = Screens.Main.route){
            HomeScreen(homeViewModel = homeViewModel, navController = navController)
        }

        composable(route = Screens.Details.route ){
            SettingsScreen(homeViewModel = homeViewModel)
        }
    }
}