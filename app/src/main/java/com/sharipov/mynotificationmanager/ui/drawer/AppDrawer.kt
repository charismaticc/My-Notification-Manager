package com.sharipov.mynotificationmanager.ui.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.utils.Constants

@Composable
fun AppDrawer(
    route: String,
    modifier: Modifier = Modifier,
    navigateToApplications: () -> Unit = {},
    navigateToAllNotifications: () -> Unit = {},
    navigateToSettings: () -> Unit = {},
    navigateToFavorite: () -> Unit = {},
    closeDrawer: () -> Unit = {}
) {
    ModalDrawerSheet(modifier = Modifier) {
        DrawerHeader(modifier)
        Spacer(modifier = Modifier.padding(16.dp))
        Column(modifier = modifier) {

            NavigationDrawerItem(
                label = { Text(text = "Applications", style = MaterialTheme.typography.bodyLarge) },
                selected = route == Constants.Screens.APPLICATION_SCREEN,
                onClick = {
                    navigateToApplications()
                    closeDrawer()
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        painter = painterResource(id = R.drawable.ic_apps),
                        contentDescription = null
                    )
                },
                shape = MaterialTheme.shapes.medium
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "All notification",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                selected = route == Constants.Screens.ALL_NOTIFICATIONS_SCREEN,
                onClick = {
                    navigateToAllNotifications()
                    closeDrawer()
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null
                    )
                },
                shape = MaterialTheme.shapes.large
            )

            NavigationDrawerItem(
                label = { Text(text = "Favorites", style = MaterialTheme.typography.bodyLarge) },
                selected = route == Constants.Screens.FAVORITE_SCREEN,
                onClick = {
                    navigateToFavorite()
                    closeDrawer()
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Default.Star,
                        contentDescription = null
                    )
                },
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.weight(1f))
            NavigationDrawerItem(
                modifier = Modifier.padding(bottom = 16.dp),
                label = { Text(text = "Settings", style = MaterialTheme.typography.bodyLarge) },
                selected = route == Constants.Screens.SETTINGS_SCREEN,
                onClick = {
                    navigateToSettings()
                    closeDrawer()
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Default.Settings,
                        contentDescription = null
                    )
                },
                shape = MaterialTheme.shapes.medium
            )
        }
    }
}


@Composable
fun DrawerHeader(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(32.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview
@Composable
fun DrawerHeaderPreview() {
    AppDrawer(modifier = Modifier, route = Constants.Screens.APPLICATION_SCREEN)
}