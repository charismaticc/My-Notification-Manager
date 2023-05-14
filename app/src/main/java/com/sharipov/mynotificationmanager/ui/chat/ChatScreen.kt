package com.sharipov.mynotificationmanager.ui.chat

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharipov.mynotificationmanager.ui.chat.components.ChatItem
import com.sharipov.mynotificationmanager.ui.topbarscomponent.ChatTopBarContent
import com.sharipov.mynotificationmanager.ui.transparentSystemBars.TransparentSystemBars
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel

@SuppressLint("FlowOperatorInvokedInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    userName: String,
    packageName: String
) {
    var searchVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val notificationFlow = if (searchText.isNotBlank()) {
        homeViewModel.searchUserNotifications(userName, packageName, searchText).collectAsState(emptyList()).value
    } else {
        homeViewModel.getAllUserNotifications(userName, packageName).collectAsState(emptyList()).value
    }

    TransparentSystemBars()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
                ChatTopBarContent(
                    userName = userName,
                    packageName = packageName,
                    searchText = searchText,
                    searchVisible = searchVisible,
                    navController = navController,
                    homeViewModel = homeViewModel,
                    onSearchClick = { searchVisible = !searchVisible },
                    onSearchVisibleChange = { searchVisible = it },
                    onSearchTextChange = { searchText = it }
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
                        visible = searchVisible
                    ) {
                        Spacer(modifier = Modifier.padding(32.dp))
                    }
                    AnimatedVisibility(
                        visible = !searchVisible || searchText.isNotEmpty(),
                    ) {
                    LazyColumn(
                        reverseLayout = true,
                        modifier = Modifier.fillMaxSize()
                    ) {

                        item { Spacer(modifier = Modifier.height(8.dp)) }

                        items(notificationFlow.size) { index ->
                            val notification = notificationFlow[index]
                            ChatItem(homeViewModel, notification)
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }
                }
            }
        }
    )
}