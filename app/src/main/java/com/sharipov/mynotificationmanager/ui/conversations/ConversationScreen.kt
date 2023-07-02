package com.sharipov.mynotificationmanager.ui.conversations

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharipov.mynotificationmanager.R
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarContent(
                title = stringResource(id = R.string.conversations),
                icon = Icons.Default.ArrowBack,
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
                    val modifier = Modifier.fillMaxSize().padding(16.dp)
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