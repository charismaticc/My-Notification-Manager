package com.sharipov.mynotificationmanager.ui.allnotifications

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.model.NotificationEntity
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.ui.allnotifications.component.NotificationItem
import com.sharipov.mynotificationmanager.ui.topbarscomponent.SearchTopBarContent
import com.sharipov.mynotificationmanager.ui.drawer.AppDrawer
import com.sharipov.mynotificationmanager.ui.transparentSystemBars.TransparentSystemBars
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AllNotificationScreen (
    homeViewModel: HomeViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    ) {

        val context = LocalContext.current
        var searchVisible by remember { mutableStateOf(false) }
        var searchText by remember { mutableStateOf("") }
        val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentNavBackStackEntry?.destination?.route ?: Constants.Screens.APPLICATION_SCREEN

        val notificationFlow = if (searchText.isNotBlank()) {
            homeViewModel.searchNotifications(searchText).collectAsState(emptyList()).value
        } else {
            homeViewModel.notificationListFlow.collectAsState(emptyList()).value
        }

        TransparentSystemBars()

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
                topBar = {
                    SearchTopBarContent(
                        title = "All notification",
                        onMenuClick = { coroutineScope.launch { drawerState.open() } },
                        onSearchClick = { searchVisible = !searchVisible },
                        searchVisible = searchVisible,
                        searchText = searchText,
                        onSearchTextChange = { searchText = it }
                    )
                },
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.padding(32.dp))

                        AnimatedVisibility(
                            visible = !searchVisible
                        ) {

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                            ) {
                                Text(
                                    text = "Count of your notification: ${notificationFlow.size}",
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(8.dp)
                                )
                            }
                        }

                        AnimatedVisibility(
                            visible = searchVisible
                        ) {
                            Spacer(modifier = Modifier.padding(32.dp))
                        }

                        AnimatedVisibility(
                            visible = !searchVisible || searchText.isNotEmpty(),
                        ) {
                            LazyColumn {
                                items(notificationFlow.size) { index ->
                                    val notification = notificationFlow[index]
                                    NotificationItem(notificationEntity = notification,
                                        Modifier
                                            .fillMaxSize()
                                            .padding(16.dp, 16.dp, 16.dp)
                                            .combinedClickable(
                                                onClick = {
                                                    navController.navigate(
                                                        Screens.Details.route +
                                                                "/${notification.id.toString()}"
                                                    )
                                                },
                                                onLongClick = {
                                                    homeViewModel.upgradeNotification(
                                                        notification = NotificationEntity(
                                                            id = notification.id,
                                                            appName = notification.appName,
                                                            packageName = notification.packageName,
                                                            user = notification.user,
                                                            text = notification.text,
                                                            time = notification.time,
                                                            favorite = !notification.favorite
                                                        )
                                                    )

                                                    val msg = if (!notification.favorite) {
                                                        "add to"
                                                    } else {
                                                        "removed from"
                                                    }

                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "Notification $msg favorite!",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                }
                                            )
                                    )
                                }
                                item { Spacer(modifier = Modifier.height(16.dp)) }
                            }
                        }
                    }
                }
            )
        }
    }