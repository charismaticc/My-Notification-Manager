package com.sharipov.mynotificationmanager.ui.allapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.ui.bottombarcomponent.BottomBar
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.ui.allapplication.component.ApplicationItem
import com.sharipov.mynotificationmanager.ui.theme.topBarColorScheme
import com.sharipov.mynotificationmanager.utils.TransparentSystemBars
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "FlowOperatorInvokedInComposition")
@Composable
fun ApplicationsScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {

    val applicationListState = homeViewModel.getApplications().collectAsState(initial = listOf())

    TransparentSystemBars()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.applications),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = topBarColorScheme()
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { Spacer(modifier = Modifier.padding(8.dp)) }
                items(applicationListState.value.size) { index ->
                    val packageName = applicationListState.value[index].packageName
                    val appName = applicationListState.value[index].appName
                    val notificationCountFlow =
                        homeViewModel.getApplicationsNotificationsCount(packageName)
                    val notificationCount by notificationCountFlow.collectAsState(initial = 0)
                    ApplicationItem(
                        packageName = packageName,
                        appName = appName,
                        notificationCount = notificationCount,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp, 8.dp, 16.dp, 8.dp)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable {
                                navController.navigate( route = "conversations/$packageName")
                            }
                    )
                }
                item { Spacer(modifier = Modifier.height(78.dp)) }
            }
        }
    }
}