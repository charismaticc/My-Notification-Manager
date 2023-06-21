package com.sharipov.mynotificationmanager.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.utils.TransparentSystemBars
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun DetailsScreen(homeViewModel: HomeViewModel, notificationId: String) {

    val notificationsFlow = homeViewModel.notificationListFlow
    val notification = notificationsFlow.map { notifications ->
        notifications.firstOrNull { it.id == notificationId.toInt() }
    }
    val notificationState = notification.collectAsState(initial = null)
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    TransparentSystemBars()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Row(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 16.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp, 8.dp, 8.dp, 8.dp)
                            .size(56.dp)
                            .align(Alignment.CenterVertically),
                    )
                    Column(Modifier.padding(16.dp, 16.dp, 16.dp, 16.dp)) {
                        Row {
                            Text(stringResource(id = R.string.from))
                            Text(
                                notificationState.value?.user ?: "None",
                                modifier = Modifier.basicMarquee()
                            )
                        }
                        Row {
                            Text(stringResource(id = R.string.in_app))
                            Text(
                                notificationState.value?.appName ?: "None",
                                modifier = Modifier.basicMarquee()
                            )
                        }
                        notificationState.value?.let { notification ->
                            Text(
                                stringResource(id = R.string.time) +
                                        dateFormat.format(Date(notification.time)).toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        } ?: run {
                            Text(stringResource(id = R.string.time_is_null))
                        }
                    }
                }
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = notificationState.value?.text ?: "Text",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            style = TextStyle(fontSize = 16.sp),
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
        }
    }
}