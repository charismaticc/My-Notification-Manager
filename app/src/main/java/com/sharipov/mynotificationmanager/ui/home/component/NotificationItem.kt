package com.sharipov.mynotificationmanager.ui.home.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharipov.mynotificationmanager.model.NotificationEntity
import com.sharipov.mynotificationmanager.navigation.Screens
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun NotificationItem(notificationEntity: NotificationEntity, navController: NavController) {

    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.padding(8.dp, 16.dp, 16.dp, 16.dp)) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(8.dp, 8.dp, 16.dp, 8.dp)
                        .size(56.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 16.dp)
                ) {
                    Text(notificationEntity.title, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                    Text(notificationEntity.user, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
                    Text(
                        notificationEntity.text,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                }
            }

            val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
            Text(
                text = dateFormat.format(Date(notificationEntity.time)),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }
    }
}