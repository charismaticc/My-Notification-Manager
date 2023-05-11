package com.sharipov.mynotificationmanager.ui.chat.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.sharipov.mynotificationmanager.model.NotificationEntity
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatItem(homeViewModel: HomeViewModel, notification: NotificationEntity) {

    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    var expanded by remember { mutableStateOf(false) }
    val clipboardManager = LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 16.dp, 16.dp)
            .clickable (
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    expanded = true
                }
            ),
        elevation = CardDefaults.cardElevation(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(8.dp, 16.dp, 16.dp, 16.dp)) {
                Text(
                    text = notification.text,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(text = dateFormat.format(Date(notification.time)))
            }

        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            DropdownMenuItem(
                text = {
                    Row() {
                        Icon(Icons.Default.Notifications, "Copy")
                        Spacer(Modifier.width(8.dp))
                        Text("Add to favorite")
                    }
                },
                onClick = {
                    clipboardManager.setPrimaryClip(ClipData.newPlainText("Text copied", notification.text))
                    expanded = false
                }
            )

            Divider()

            DropdownMenuItem(
                text = {
                    Row() {
                        Icon(Icons.Default.Favorite, "Add to favorite")
                        Spacer(Modifier.width(8.dp))
                        Text("Add to favorite")
                    }
                },
                onClick = {
                    homeViewModel.upgradeNotification(
                        NotificationEntity(
                            id = notification.id,
                            appName = notification.appName,
                            packageName = notification.packageName,
                            user = notification.user,
                            text = notification.text,
                            time = notification.time,
                            favorite = true
                        )
                    )
                    expanded = false
                }
            )

            Divider()

            DropdownMenuItem(
                text = {
                    Row() {
                        Icon(Icons.Default.Delete, "Delete notification")
                        Spacer(Modifier.width(8.dp))
                        Text("Delete")
                    }
                },
                onClick = {
                    homeViewModel.deleteNotification(notification)
                    expanded = false
                }
            )
        }
    }
}
