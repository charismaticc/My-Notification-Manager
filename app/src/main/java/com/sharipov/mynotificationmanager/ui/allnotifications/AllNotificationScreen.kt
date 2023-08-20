package com.sharipov.mynotificationmanager.ui.allnotifications

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.system.exitProcess

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
    var showStatistic by remember { mutableStateOf(false) }
    val dates = getWeekDays()
    val dummyData = getDummy(homeViewModel)

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
            if(searchVisible) {
                searchVisible = false
            } else if(showStatistic) {
                showStatistic = false
            } else if(navController.currentBackStackEntry?.destination?.route == Screens.AllNotifications.route) {
                exitProcess(0)
            }
            else {
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
                        .clickable {
                            showStatistic = !showStatistic
                        }
                ) {
                    Text(
                        text = "${stringResource(id = R.string.count_of_your_notification)} " +
                                "${notificationFlow.size}",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                    )
                    AnimatedVisibility(
                        visible = showStatistic
                    ) {
                        BarChart(dates, dummyData)
                    }
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
                            notification = notification,
                            navController = navController,
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
fun getDummy(homeViewModel: HomeViewModel): List<Int> {
    val dateList = mutableListOf<Int>()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, -7)
    for (i in 0..6) {
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val data = dateFormat.format(calendar.time)
        dateList += getNotificationFlow(homeViewModel, "", data, data).size
    }
    return dateList
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

fun getWeekDays(): List<String> {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EE", Locale.getDefault())

    val daysOfWeek = mutableListOf<String>()

    for (i in 0 until 7) {
        daysOfWeek.add(dateFormat.format(calendar.time))
        calendar.add(Calendar.DAY_OF_WEEK, -1)
    }
    return daysOfWeek.reversed()
}
@Composable
fun BarChart(dates: List<String>, data: List<Int>, modifier: Modifier = Modifier) {
    var maxCount by remember { mutableIntStateOf(0) }
    val color = MaterialTheme.colorScheme.primary

    data.forEach { count ->
        if (count > maxCount) {
            maxCount = count
        }
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEachIndexed { _, date ->
                Text(
                    text = date.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = color
                )
            }
        }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(300.dp) // Set the height of the chart
        ) {
            val barWidth = size.width / (data.size * 2) // Width of each bar
            val spaceBetweenBars = size.width / (data.size * 4) // Space between bars

            data.forEachIndexed { index, count ->
                val barHeight = size.height * count / maxCount
                val startX = (index * (barWidth * 2)) + spaceBetweenBars
                val startY = size.height - barHeight
                drawRect(
                    color = color,
                    size = Size(barWidth, barHeight),
                    topLeft = Offset(startX, startY),
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dates.forEachIndexed { _, date ->
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = color
                )
            }
        }
    }
}
