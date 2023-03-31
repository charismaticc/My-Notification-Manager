@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("UNUSED_EXPRESSION")

package com.sharipov.mynotificationmanager.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.sharipov.mynotificationmanager.ui.home.component.NotificationItem
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel

import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {
    var searchVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val notificationListState = homeViewModel.notificationListFlow.collectAsState(initial = listOf())

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
                navigationIcon  = {
                    IconButton(onClick = { /* doSomething() */ }) {
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
                verticalArrangement = Arrangement.Center,
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
                    )) {
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
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    items(notificationListState.value.size) { index ->
                        val notification = notificationListState.value[index]
                        if (searchText.lowercase() in notification.title.lowercase() ||
                            searchText.lowercase() in notification.user.lowercase() ||
                            searchText.lowercase() in notification.text.lowercase() ||
                            searchText == "") {
                            NotificationItem(notificationEntity = notification)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    )
}


//@Preview
//@Composable
//fun PreviewHomeScreen(){
//    HomeScreen(
//        homeViewModel = object : HomeViewModelAbstract {
//            override val notificationListFlow: Flow<List<NotificationEntity>>
//                get() = flowOf(listOf(
//                    NotificationEntity(0,"Application name", "User name", "Message text Message text Message text Message text Message text", System.currentTimeMillis()),
//                    NotificationEntity(1,"Application name", "User name", "Message text Message text Message text Message text Message text", System.currentTimeMillis()),
//                    NotificationEntity(2,"Application name", "User name", "Message text Message text Message text Message text Message text", System.currentTimeMillis()),
//                    NotificationEntity(3,"Application name", "User name", "Message text Message text Message text Message text Message text", System.currentTimeMillis())
//                ))
//
//            override fun addNotification(notification: NotificationEntity) {}
//
//            override fun upgradeNotification(notification: NotificationEntity) {}
//
//            override fun deleteNotification(notification: NotificationEntity) {}
//
//        }
//    )
//}