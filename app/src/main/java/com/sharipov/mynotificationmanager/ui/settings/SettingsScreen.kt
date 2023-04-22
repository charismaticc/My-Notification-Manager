package com.sharipov.mynotificationmanager.ui.settings


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.ui.drawer.AppDrawer
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Constants.Screens.SETTINGS_SCREEN

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                route = currentRoute,
                navigateToApplications = { navController.navigate(Screens.Applications.route) },
                navigateToAllNotifications = { navController.navigate(Screens.AllNotifications.route) },
                navigateToSettings = { navController.navigate(Screens.Settings.route) },
                navigateToFavorite = { navController.navigate(Screens.Favorite.route) },
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
                modifier = Modifier
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier  = Modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Settings",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { drawerState.open() }
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                )
            },
            content = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(1) {
                        Card(modifier = Modifier.fillParentMaxWidth().padding(16.dp)) {
                            Text("Display theme")
                        }

                        Card(modifier = Modifier.fillParentMaxWidth().padding(16.dp)) {
                            Text("Select apps")
                        }

                        Card(modifier = Modifier.fillParentMaxWidth().padding(16.dp)) {
                            Text("Private policy")
                        }

                        Card(modifier = Modifier.fillParentMaxWidth().padding(16.dp)) {
                            Text("Feedback")
                        }

                        Card(modifier = Modifier.fillParentMaxWidth().padding(16.dp)) {
                            Text("About us")
                        }
                    }

//                    item {
//                        Card(modifier = Modifier.fillParentMaxWidth().padding(16.dp)) {
//                            Text("Display theme")
//                        }
//                    }
//
//                    item {
//                        Card(modifier = Modifier.fillParentMaxWidth().padding(16.dp)) {
//                            Text("Select apps")
//                        }
//                    }
//
//                    item {
//                        Card(modifier = Modifier.fillParentMaxWidth().padding(16.dp)) {
//                            Text("Private policy")
//                        }
//                    }
//
//                    item {
//                        Card(modifier = Modifier.fillParentMaxWidth().padding(16.dp)) {
//                            Text("Feedback")
//                        }
//                    }
//
//                    item {
//                        Card(modifier = Modifier.fillParentMaxWidth().padding(16.dp)) {
//                            Text("About us")
//                        }
//                    }
                }
            }
        )
    }
}

