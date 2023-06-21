package com.sharipov.mynotificationmanager.ui.allnotifications.component

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.model.NotificationEntity
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun NotificationItem(homeViewModel: HomeViewModel, notification: NotificationEntity, modifier: Modifier) {

    val context = LocalContext.current

    // get app icon
    val appIconDrawable = context.packageManager.getApplicationIcon(notification.packageName)

    // data format
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    val icon: ImageVector
    val color: Color

    if (notification.favorite) {
        icon = Icons.Default.Star
        color = MaterialTheme.colorScheme.primary
    } else {
        icon = Icons.Outlined.Star
        color = MaterialTheme.colorScheme.inversePrimary
    }

    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                Image(
                    painter = rememberDrawablePainter(appIconDrawable),
                    contentDescription = "App icon",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(74.dp)
                        .padding(8.dp, end = 16.dp),
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 8.dp)
                ) {
                    Text(notification.appName, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                    Text(notification.user, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
                    Text(
                        notification.text,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = dateFormat.format(Date(notification.time)),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Image(
                            imageVector = icon,
                            contentDescription = "",
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    updateNotification(notification, homeViewModel, context)
                                },
                            colorFilter = ColorFilter.tint(color)
                        )
                    }
                }
            }
        }
    }
}

fun updateNotification(notificationEntity: NotificationEntity, homeViewModel: HomeViewModel, context: Context) {
    val notification = NotificationEntity(
        id = notificationEntity.id,
        appName = notificationEntity.appName,
        packageName = notificationEntity.packageName,
        user = notificationEntity.user,
        text = notificationEntity.text,
        time = notificationEntity.time,
        favorite = !notificationEntity.favorite
    )

    homeViewModel.upgradeNotification(notification = notification)

    val msg = getMessage(context = context, notification.favorite)
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun getMessage(context: Context, isFavorite: Boolean): String {
    val resourceId = if (isFavorite) {
        R.string.notification_added_to_favorites
    } else {
        R.string.notification_removed_from_favorites
    }
    return context.getString(resourceId)
}