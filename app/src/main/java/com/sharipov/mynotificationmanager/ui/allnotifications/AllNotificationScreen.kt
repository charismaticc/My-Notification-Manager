package com.sharipov.mynotificationmanager.ui.allnotifications

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.model.NotificationEntity
import com.sharipov.mynotificationmanager.ui.drawer.AppDrawer
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.ui.allnotifications.component.NotificationItem
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
        val notificationListState = homeViewModel.notificationListFlow.collectAsState(initial = listOf())



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
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                "All notification",
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
                        actions = {
                            IconButton(onClick = { searchVisible = !searchVisible }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    )
                },
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(64.dp))

                        AnimatedVisibility(
                            visible = !searchVisible
                        ) {

                            Card(
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                            ) {
                                Text(
                                    text = "Count of your notification: ${notificationListState.value.size}",
                                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp)
                                )
                            }
                        }

                        AnimatedVisibility(
                            visible = searchVisible,
                        ) {
                            OutlinedTextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                label = { Text("Search") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        searchVisible = false
                                    }
                                )
                            )
                        }

                        AnimatedVisibility(
                            visible = !searchVisible || searchText.isNotEmpty(),
                        ) {
                            LazyColumn {
                                items(notificationListState.value.size) { index ->
                                    val notification = notificationListState.value[index]
                                    if (searchText.lowercase() in notification.packageName.lowercase() ||
                                        searchText.lowercase() in notification.user.lowercase() ||
                                        searchText.lowercase() in notification.text.lowercase() &&
                                        searchText != ""
                                    ) {
                                        NotificationItem(notificationEntity = notification,
                                            Modifier.fillMaxSize().padding(16.dp, 16.dp, 16.dp)
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

                                                        val msg = if(!notification.favorite) {
                                                            "add to"
                                                        } else {
                                                            "removed from"
                                                        }

                                                        Toast.makeText(
                                                            context,
                                                            "Notification $msg favorite!",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                )
                                        )
                                    }
                                }
                                item { Spacer(modifier = Modifier.height(16.dp)) }
                            }
                        }
                    }
                }
            )
        }
    }