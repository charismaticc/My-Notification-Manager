package com.sharipov.mynotificationmanager.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material3.Button
import com.sharipov.mynotificationmanager.R


@Composable
fun PermissionScreen(
    permission: List<Pair<String, Boolean>>?,
    navController: NavController
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.access_to_features),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.padding(32.dp))

        Text(
            text = stringResource(id = R.string.request_access_description),
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
        )

        for (i in permission ?: emptyList()) {
            when (i.first) {
                "NotificationListener" -> PermissionItem(
                    stringResource(id = R.string.notifications),
                    stringResource(id = R.string.notifications_description),
                    painterResource(id = R.drawable.ic_notifications)
                )

                "BatteryOptimizationIgnore" -> PermissionItem(
                    stringResource(id = R.string.battery_optimization),
                    stringResource(id = R.string.battery_optimization_description),
                    painterResource(id = R.drawable.ic_battery)
                )
            }
        }

        Button(onClick = { getPermission(context) }) {
            Text(stringResource(id = R.string.give_permissions))
        }
    }
}

fun getPermission(context: Context) {
    Toast.makeText(context, "TOAST", Toast.LENGTH_LONG).show()
}


@Composable
fun PermissionItem(permission: String, description: String, icon: Painter) {
    val modifier = Modifier
        .padding(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 8.dp
        )
        .fillMaxWidth()

    HorizontalDivider(
        modifier = modifier,
        thickness = 2.dp
    )
    Row(modifier = modifier) {
        Icon(
            painter = icon,
            modifier = Modifier.size(64.dp),
            contentDescription = null
        )
        Column(Modifier.padding(start = 8.dp)) {
            Text(
                text = permission,
                modifier = modifier.size(32.dp),
                style = androidx.compose.ui.text.TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )

            Text(
                text = description,
                modifier = modifier
            )
        }
    }
}