@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("UNUSED_EXPRESSION")

package com.sharipov.mynotificationmanager.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.AppDrawer
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.ui.home.component.NotificationItem
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    var searchVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Constants.Screens.MAIN_SCREEN

    val notificationListState = homeViewModel.notificationListFlow.collectAsState(initial = listOf())

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                route = currentRoute,
                navigateToHome = { navController.navigate(Screens.Main.route) },
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
                            "Notifications",
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
                    Spacer(modifier = Modifier.height(48.dp))
                    AnimatedVisibility(
                        visible = searchVisible,
                        enter = slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(500)
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { -it },
                            animationSpec = tween(500)
                        )
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
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                        items(notificationListState.value.size) { index ->
                            val notification = notificationListState.value[index]
                            if (searchText.lowercase() in notification.title.lowercase() ||
                                searchText.lowercase() in notification.user.lowercase() ||
                                searchText.lowercase() in notification.text.lowercase() &&
                                searchText != ""
                            ) {
                                NotificationItem(notificationEntity = notification,
                                    Modifier.fillMaxSize().padding(16.dp, 16.dp, 16.dp).clickable {
                                        navController.navigate(
                                            Screens.Details.route +
                                                    "/${notification.id.toString()}"
                                        )
                                    }
                                )
                            }
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }
                }
            }
        )
    }
}