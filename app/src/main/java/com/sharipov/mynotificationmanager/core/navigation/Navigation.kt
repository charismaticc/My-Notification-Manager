package com.sharipov.mynotificationmanager.core.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sharipov.mynotificationmanager.core.navigation.bottomNavBar.BottomNavigationBar
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.allNotificationsGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.applicationsGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.favoriteGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.mainGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.notificationFilterGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.settingsGraph

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
            startDestination = "main_graph",
        ) {
            mainGraph(navController = navController)
            favoriteGraph(navController = navController)
            applicationsGraph(navController = navController)
            allNotificationsGraph(navController = navController)
            notificationFilterGraph(navController = navController)
            settingsGraph(navController = navController)
        }
    }
}