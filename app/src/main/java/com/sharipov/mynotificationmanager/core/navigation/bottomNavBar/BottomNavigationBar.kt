package com.sharipov.mynotificationmanager.core.navigation.bottomNavBar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.R

data class BottomNavItem(
    val title: String,
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val items = listOf(
        BottomNavItem(
            title = "Favorite",
            route = "favorite_graph",
            selectedIcon = R.drawable.ic_archive_filled,
            unselectedIcon = R.drawable.ic_archive_filled,
            hasNews = false
        ),
        BottomNavItem(
            title = "Apps",
            route = "applications_graph",
            selectedIcon = R.drawable.ic_apps_filled,
            unselectedIcon = R.drawable.ic_apps_filled,
            hasNews = false
        ),
        BottomNavItem(
            title = "All",
            route = "all_notifications_graph",
            selectedIcon = R.drawable.ic_notifications_filled,
            unselectedIcon = R.drawable.ic_notifications_filled,
            hasNews = false
        ),
        BottomNavItem(
            title = "Rules",
            route = "notification_filter_graph",
            selectedIcon = R.drawable.ic_rule_filled,
            unselectedIcon = R.drawable.ic_rule_filled,
            hasNews = false
        ),
        BottomNavItem(
            title = "Settings",
            route = "settings_graph",
            selectedIcon = R.drawable.ic_settings_filled,
            unselectedIcon = R.drawable.ic_settings_filled,
            hasNews = false
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentGraphName = navBackStackEntry?.destination?.parent?.route
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentGraphName == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.selectedIcon),
                        contentDescription = item.title
                    )
                },
            )
        }
    }
}