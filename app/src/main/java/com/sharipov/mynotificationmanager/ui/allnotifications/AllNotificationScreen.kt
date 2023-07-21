package com.sharipov.mynotificationmanager.ui.allnotifications

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.sharipov.mynotificationmanager.ui.bottombarcomponent.BottomBar
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.model.NotificationEntity
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.ui.allnotifications.component.MyFloatingActionButton
import com.sharipov.mynotificationmanager.ui.allnotifications.component.NotificationItem
import com.sharipov.mynotificationmanager.ui.allnotifications.component.bottomSheet
import com.sharipov.mynotificationmanager.ui.topbarscomponent.SearchTopBarContent
import com.sharipov.mynotificationmanager.utils.TransparentSystemBars
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.utils.dateConverter
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import kotlin.system.exitProcess

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun AllNotificationScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {

    val context = LocalContext.current
    var searchVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var fromDate by remember { mutableStateOf("") }
    var toDate by remember { mutableStateOf("") }
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Constants.Screens.APPLICATION_SCREEN
    val notificationFlow = getNotificationFlow(homeViewModel, searchText, fromDate, toDate)
    var showFilters by remember { mutableStateOf(false) }

    TransparentSystemBars()

    Scaffold(
        topBar = {
            SearchTopBarContent(
                title = stringResource(id = R.string.all_notification),
                onSearchClick = { searchVisible = !searchVisible },
                searchVisible = searchVisible,
                searchText = searchText,
                onSearchTextChange = { searchText = it }
            )
        },
        bottomBar = {
            BottomBar(
                route = currentRoute,
                navigateToApplications = { navController.navigate(Screens.Applications.route) },
                navigateToAllNotifications = { navController.navigate(Screens.AllNotifications.route) },
                navigateToSettings = { navController.navigate(Screens.Settings.route) },
                navigateToFavorite = { navController.navigate(Screens.Favorite.route) },
                navigateToNotificationManagement = { navController.navigate(Screens.NotificationManagement.route) },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(onFiltersClick = { showFilters = !showFilters })
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        BackHandler {
            if(navController.currentBackStackEntry?.destination?.route == Screens.AllNotifications.route) {
                exitProcess(0)
            } else {
                navController.popBackStack()
            }
        }
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
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
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
                    modifier = Modifier.fillMaxSize()
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
                    item { Spacer(modifier = Modifier.height(156.dp)) }
                }
            }
            data = bottomSheet(showFilters) { showFilters = false }
            fromDate = data.split(":")[0]
            toDate = data.split(":")[1]
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getNotificationFlow(
    homeViewModel: HomeViewModel,
    searchText: String,
    fromDate: String,
    toDate: String
): List<NotificationEntity> {
    return if (searchText.isNotBlank()) {
        homeViewModel.searchNotifications(searchText).collectAsState(emptyList()).value
    } else if(fromDate.isNotBlank() || toDate.isNotBlank()) {
        val fromDateLongValue = dateConverter("from", fromDate)
        val toDateLongValue = dateConverter("to", toDate)
        homeViewModel.getNotificationsFromData(fromDateLongValue, toDateLongValue).collectAsState(emptyList()).value
    }else {
        homeViewModel.notificationListFlow.collectAsState(emptyList()).value
    }
}