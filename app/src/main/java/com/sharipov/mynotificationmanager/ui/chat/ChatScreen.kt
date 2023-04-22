package com.sharipov.mynotificationmanager.ui.chat

import android.annotation.SuppressLint

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharipov.mynotificationmanager.ui.chat.components.ChatItem
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun ChatScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    userName: String,
    packageName: String
) {
    val notificationsFlow = homeViewModel.getAllUserNotifications(userName, packageName)
    val notificationListState = notificationsFlow.collectAsState(initial = listOf())

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = userName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Arrow back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {  expanded = true
                        }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More vert"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .offset {
                                IntOffset(0, 0)
                            }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Row() {
                                    Icon(Icons.Default.Search, "Search in chat")
                                    Spacer(Modifier.width(8.dp))
                                    Text("Find")
                                }
                            },
                            onClick = {
                                //
                                expanded = false
                            },

                            )

                        Divider()

                        DropdownMenuItem(
                            text = {
                                Row() {
                                    Icon(Icons.Default.Delete, "Delete chat")
                                    Spacer(Modifier.width(8.dp))
                                    Text("Delete chat")
                                }
                            },
                            onClick = {
                                homeViewModel.deleteNotificationsForUser(userName, packageName)
                                navController.navigateUp()
                                expanded = false
                            }
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

                LazyColumn(
                    reverseLayout = true,
                    modifier = Modifier.fillMaxSize()
                ) {

                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    items(notificationListState.value.size) { index ->
                        val notification = notificationListState.value[index]
                        ChatItem(homeViewModel, notification)
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }

                }
            }
        }
    )
}