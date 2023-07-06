package com.sharipov.mynotificationmanager.ui.conversations

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharipov.mynotificationmanager.ui.topbarscomponent.TopBarContent
import com.sharipov.mynotificationmanager.ui.conversations.component.UserItem
import com.sharipov.mynotificationmanager.utils.TransparentSystemBars
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
@SuppressLint("FlowOperatorInvokedInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConversationsScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    packageName: String,
) {
    val userNamesFlow = homeViewModel.getApplicationUserNames(packageName)
    val usersListState by userNamesFlow.collectAsState(initial = listOf())

    TransparentSystemBars()

    val context = LocalContext.current
    val pm = context.packageManager
    // get app icon
    val appIcon = pm.getApplicationIcon(packageName)
    val appName = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarContent(
                title =  appName,
                icon = Icons.Default.ArrowBack,
                appIcon = appIcon,
                onNavigationClick = { navController.navigateUp() }
            )
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(usersListState) { userName ->
                    val modifier = Modifier.fillMaxSize().padding(16.dp, 16.dp, 16.dp)
                    UserItem(
                        homeViewModel = homeViewModel,
                        navController = navController,
                        userName = userName,
                        packageName = packageName,
                        userCount = usersListState.size-1,
                        modifier = modifier
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}