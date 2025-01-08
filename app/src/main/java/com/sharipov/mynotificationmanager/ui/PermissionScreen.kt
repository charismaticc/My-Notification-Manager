package com.sharipov.mynotificationmanager.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.utils.Constants

@Composable
fun PermissionScreen(navController: NavController) {
    val context = LocalContext.current
    val permissions = getPermissionList(context)

    LaunchedEffect(permissions) {
        if (permissions.all { !it.second }) {
            navController.navigate("all_notifications_graph")
            Log.d("TAG", "PermissionScreen: launch all_notifications_graph")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText(text = stringResource(R.string.access_to_features))

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.request_access_description),
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        permissions.filter { it.second }.forEach { (type, isGranted) ->
            PermissionItem(
                permission = when (type) {
                    "NotificationListener" -> stringResource(R.string.notifications)
                    "BatteryOptimizationIgnore" -> stringResource(R.string.battery_optimization)
                    else -> "Unknown"
                },
                description = when (type) {
                    "NotificationListener" -> stringResource(R.string.notifications_description)
                    "BatteryOptimizationIgnore" -> stringResource(R.string.battery_optimization_description)
                    else -> "Unknown"
                },
                icon = painterResource(
                    id = when (type) {
                        "NotificationListener" -> R.drawable.ic_save_notifications
                        "BatteryOptimizationIgnore" -> R.drawable.ic_battery
                        else -> R.drawable.ic_launcher_foreground
                    }
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                requestPermissions(context)
                navController.navigate(Constants.Screens.ALL_NOTIFICATIONS_SCREEN)
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(R.string.give_permissions),
                color = Color.White
            )
        }
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
    )
}


fun getPermissionList(context: Context): List<Pair<String, Boolean>> {
    return listOf(
        "NotificationListener" to !NotificationManagerCompat
            .getEnabledListenerPackages(context)
            .contains(context.packageName),
        "BatteryOptimizationIgnore" to !isBatteryOptimizationIgnored(context)
    )
}

fun isBatteryOptimizationIgnored(context: Context): Boolean {
    val powerManager = ContextCompat.getSystemService(context, PowerManager::class.java)
    return powerManager?.isIgnoringBatteryOptimizations(context.packageName) ?: false
}

@SuppressLint("BatteryLife")
fun requestBatteryOptimization(context: Context) {
    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
        data = Uri.parse("package:${context.packageName}")
    }
    ContextCompat.startActivity(context, intent, bundleOf())
}


fun requestPermissions(context: Context) {
    val permissions = getPermissionList(context)
    permissions.forEach { (type, isGranted) ->
        if (isGranted) {
            when (type) {
                "NotificationListener" -> {
                    val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                    ContextCompat.startActivity(context, intent, bundleOf())
                }
                "BatteryOptimizationIgnore" -> requestBatteryOptimization(context)
            }
        }
    }
}

@Composable
fun PermissionItem(permission: String, description: String, icon: Painter) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        HorizontalDivider(thickness = 2.dp)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = icon,
                modifier = Modifier.size(64.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = permission,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
