package com.sharipov.mynotificationmanager.ui.allnotifications.component

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailsBottomSheet(
    showNotification: Boolean,
    homeViewModel: HomeViewModel,
    navController: NavController,
    notificationId: String,
    showNotificationValue: () -> Unit,
    ) {
    val sheetState = rememberSheetState()
    val scope = rememberCoroutineScope()

    if (showNotification) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }
                showNotificationValue()
            },
        ) {
            NotificationDetailsBottomSheetContent(
                homeViewModel = homeViewModel,
                notificationId = notificationId,
                navController = navController
            ){
                showNotificationValue()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun NotificationDetailsBottomSheetContent(
    homeViewModel: HomeViewModel,
    notificationId: String,
    navController: NavController,
    showNotificationValue: () -> Unit
){
    val notificationsFlow = homeViewModel.notificationListFlow
    val notification = notificationsFlow.map { notifications ->
        notifications.firstOrNull { it.id == notificationId.toInt() }
    }
    val notificationState = notification.collectAsState(initial = null)
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
    val clipboardManager =
        LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val context = LocalContext.current

    val appIconDrawable : Drawable? = try {
        notificationState.value?.let { context.packageManager.getApplicationIcon(it.packageName) }
    } catch (e: PackageManager.NameNotFoundException) {
        ContextCompat.getDrawable(context, R.drawable.ic_notifications)
    }
    Surface(
        modifier = Modifier.wrapContentSize()
    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberDrawablePainter(appIconDrawable),
                        contentDescription = "App icon",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(56.dp)
                            .align(Alignment.CenterVertically)
                            .clip(CircleShape),
                    )
                    Column(Modifier.padding(16.dp)) {
                        Row {
                            Text(stringResource(id = R.string.from) + " ")
                            Text(
                                notificationState.value?.user ?: "None",
                                modifier = Modifier.basicMarquee()
                            )
                        }
                        Row {
                            Text(stringResource(id = R.string.in_app) + " ")
                            Text(
                                notificationState.value?.appName ?: "None",
                                modifier = Modifier.basicMarquee()
                            )
                        }
                        notificationState.value?.let { notification ->
                            Text(
                                stringResource(id = R.string.time) + " " +
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
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
                                .padding(8.dp)
                                .verticalScroll(rememberScrollState()),
                            style = TextStyle(fontSize = 16.sp),
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
            Row {
                Button(
                    onClick = {
                        clipboardManager.setPrimaryClip(
                            ClipData.newPlainText("Text copied", notificationState.value?.text ?: "Text")
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.copy),
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.titleSmall
                            .copy(fontWeight = FontWeight.Bold)
                    )
                }

                Button(
                    onClick = {
                        notificationState.value?.let { notification ->
                            homeViewModel.deleteNotification(notification)
                        }
                        showNotificationValue()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)

                ) {
                    Text(
                        text = stringResource(id = R.string.delete),
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.titleSmall
                            .copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
            Button(
                onClick = {
                    notificationState.value?.let { notification ->
                        navController.navigate(Screens.Chat.route + "/${notification.user}/${notification.packageName}/${notification.group}")
                    }
                    showNotificationValue()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, bottom = 32.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.go_to_the_chat),
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleSmall
                        .copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}