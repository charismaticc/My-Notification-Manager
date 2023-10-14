package com.sharipov.mynotificationmanager.ui.chat.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.ContentAlpha
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.model.NotificationEntity
import com.sharipov.mynotificationmanager.ui.allnotifications.component.updateNotification
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatItem(homeViewModel: HomeViewModel, notification: NotificationEntity) {
    val context = LocalContext.current

    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    var expanded by remember { mutableStateOf(false) }
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 16.dp, 16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    expanded = true
                }
            ),
        elevation = CardDefaults.cardElevation(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(8.dp, 16.dp, 16.dp, 8.dp)) {
                if(notification.group != "not_group") {
                    Text(
                        text = notification.user,
                        modifier = Modifier.padding(start = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = notification.text,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                    fontSize = 16.sp
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                ){
                    Text(
                        text = dateFormat.format(Date(notification.time)),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.medium)
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
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            DropdownMenuItem(
                text = {
                    Row{
                        Icon(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(end = 16.dp),
                            painter = painterResource(R.drawable.ic_content_copy),
                            contentDescription = "Copy",
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.copy),
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                                .fillMaxWidth()
                        )
                    }
                },
                onClick = {
                    clipboardManager.setPrimaryClip(
                        ClipData.newPlainText("Text copied", notification.text)
                    )
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = {
                    Row{
                        Icon(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(end = 16.dp),
                            painter = painterResource(R.drawable.ic_star),
                            contentDescription = "Add/Delete to/from favorite",
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if(notification.favorite) stringResource(id = R.string.delete_from_favorite)
                                else stringResource(id = R.string.add_to_favorite),
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                                .fillMaxWidth()
                        )
                    }
                },
                onClick = {
                    homeViewModel.upgradeNotification(
                        NotificationEntity(
                            id = notification.id,
                            appName = notification.appName,
                            packageName = notification.packageName,
                            group = notification.group,
                            user = notification.user,
                            text = notification.text,
                            time = notification.time,
                            favorite = true
                        )
                    )
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = {
                    Row{
                        Icon(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(end = 16.dp),
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "Delete notification",
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.delete),
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                                .fillMaxWidth()
                        )
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
