package com.sharipov.mynotificationmanager.ui.allnotifications

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.ui.allnotifications.component.MyFloatingActionButton
import com.sharipov.mynotificationmanager.ui.allnotifications.component.NotificationItem
import com.sharipov.mynotificationmanager.ui.allnotifications.component.bottomSheet
import com.sharipov.mynotificationmanager.ui.topbarscomponent.SearchTopBarContent
import com.sharipov.mynotificationmanager.ui.drawer.AppDrawer
import com.sharipov.mynotificationmanager.utils.TransparentSystemBars
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.utils.dateConverter
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun AllNotificationScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {

    val context = LocalContext.current
    var searchVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var fromDate by remember { mutableStateOf("") }
    var toDate by remember { mutableStateOf("") }
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Constants.Screens.APPLICATION_SCREEN
    val notificationFlow = if (searchText.isNotBlank()) {
        homeViewModel.searchNotifications(searchText).collectAsState(emptyList()).value
    } else if(fromDate.isNotBlank() || toDate.isNotBlank()) {
        val fromDateLongValue = dateConverter(fromDate)
        val toDateLongValue = dateConverter(toDate)
        homeViewModel.getNotificationsFromData(fromDateLongValue, toDateLongValue).collectAsState(emptyList()).value
    }else {
        homeViewModel.notificationListFlow.collectAsState(emptyList()).value
    }
    var showFilters by remember { mutableStateOf(false) }

    TransparentSystemBars()

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                route = currentRoute,
                navigateToApplications = { navController.navigate(Screens.Applications.route) },
                navigateToAllNotifications = { navController.navigate(Screens.AllNotifications.route) },
                navigateToSettings = { navController.navigate(Screens.Settings.route) },
                navigateToFavorite = { navController.navigate(Screens.Favorite.route) },
                navigateToNotificationManagement = { navController.navigate(Screens.NotificationManagement.route) },
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
                modifier = Modifier
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                SearchTopBarContent(
                    title = stringResource(id = R.string.all_notification),
                    onMenuClick = { coroutineScope.launch { drawerState.open() } },
                    onSearchClick = { searchVisible = !searchVisible },
                    searchVisible = searchVisible,
                    searchText = searchText,
                    onSearchTextChange = { searchText = it }
                )
            },
            floatingActionButton = {
                MyFloatingActionButton(onFiltersClick = { showFilters = !showFilters })
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(32.dp))

                if (!searchVisible) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.count_of_your_notification)} " +
                                    "${notificationFlow.size}",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(8.dp)
                        )
                    }
                }

                if (searchVisible) {
                    Spacer(modifier = Modifier.padding(32.dp))
                }

                AnimatedVisibility(
                    visible = !searchVisible || searchText.isNotEmpty(),
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(notificationFlow.size) { index ->
                            val notification = notificationFlow[index]
                            NotificationItem(
                                homeViewModel = homeViewModel,
                                navController = navController,
                                notification = notification,
                                context = context,
                            )
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }
                }
                data = bottomSheet(showFilters) { showFilters = false }
                fromDate = data.split(":")[0]
                toDate = data.split(":")[1]
            }
        }
    }
}