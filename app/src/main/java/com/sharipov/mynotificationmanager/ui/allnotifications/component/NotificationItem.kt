package com.sharipov.mynotificationmanager.ui.allnotifications.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.sharipov.mynotificationmanager.model.NotificationEntity
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun NotificationItem(notificationEntity: NotificationEntity, modifier: Modifier) {

    val context = LocalContext.current

    // get app icon
    val appIconDrawable = context.packageManager.getApplicationIcon(notificationEntity.packageName)

    // data format
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.padding(start = 16.dp, end =  16.dp, bottom =  16.dp)) {
                Image(
                    painter = rememberDrawablePainter(appIconDrawable),
                    contentDescription = "App icon",
                    modifier = Modifier.size(74.dp).padding(8.dp, end = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 8.dp)
                ) {
                    Text(notificationEntity.appName, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                    Text(notificationEntity.user, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
                    Text(
                        notificationEntity.text,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                    Text(
                        text = dateFormat.format(Date(notificationEntity.time)),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 8.dp)
                    )
                }
            }

        }
    }
}