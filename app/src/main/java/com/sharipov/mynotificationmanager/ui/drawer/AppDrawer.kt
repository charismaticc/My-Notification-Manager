package com.sharipov.mynotificationmanager.ui.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.ui.drawer.component.MyNavigationDrawerItem
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

            MyNavigationDrawerItem(
                label = stringResource(id = R.string.applications),
                selected = route == Constants.Screens.APPLICATION_SCREEN,
                onClick = {
                    navigateToApplications()
                    closeDrawer()
                },
                icon = painterResource(id = R.drawable.ic_apps),
            )
            Spacer(modifier = Modifier.padding(2.dp))
            MyNavigationDrawerItem(
                label = stringResource(id = R.string.all_notification),
                selected = route == Constants.Screens.ALL_NOTIFICATIONS_SCREEN,
                onClick = {
                    navigateToAllNotifications()
                    closeDrawer()
                },
                icon = painterResource(id = R.drawable.ic_notifications),
            )
            Spacer(modifier = Modifier.padding(2.dp))
            MyNavigationDrawerItem(
                label = stringResource(id = R.string.favorites),
                selected = route == Constants.Screens.FAVORITE_SCREEN,
                onClick = {
                    navigateToFavorite()
                    closeDrawer()
                },
                icon = painterResource(id = R.drawable.ic_star),
            )

            Spacer(modifier = Modifier.weight(1f))

            MyNavigationDrawerItem(
                label = stringResource(id = R.string.settings),
                selected = route == Constants.Screens.SETTINGS_SCREEN,
                onClick = {
                    navigateToSettings()
                    closeDrawer()
                },
                icon = painterResource(id = R.drawable.ic_settings),
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
