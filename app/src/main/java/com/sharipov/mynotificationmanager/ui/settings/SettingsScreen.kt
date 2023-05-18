package com.sharipov.mynotificationmanager.ui.settings


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.ui.drawer.AppDrawer
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.ui.topbarscomponent.TopBarContent
import com.sharipov.mynotificationmanager.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.sharipov.mynotificationmanager.ui.settings.components.ClickableListItem
import com.sharipov.mynotificationmanager.ui.settings.components.aboutUsDialog
import com.sharipov.mynotificationmanager.ui.settings.components.autoRemoveDialog
import com.sharipov.mynotificationmanager.ui.settings.components.feedbackDialog
import com.sharipov.mynotificationmanager.ui.settings.components.privatePolicyDialog
import com.sharipov.mynotificationmanager.ui.settings.components.selectAppsDialog
import com.sharipov.mynotificationmanager.ui.transparentSystemBars.TransparentSystemBars
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Constants.Screens.SETTINGS_SCREEN

    val openAutoRemoveDialog = remember { mutableStateOf(false) }
    val openSelectAppsDialog = remember { mutableStateOf(false) }
    val openPrivatePolicyDialog = remember { mutableStateOf(false) }
    val openFeedbackDialog = remember { mutableStateOf(false) }
    val openAboutUsDialog = remember { mutableStateOf(false) }

    var selectedTime by remember { mutableStateOf("Newer") }
    val showAutoDeleteDialog= remember { mutableStateOf(false) }

    TransparentSystemBars()

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                route = currentRoute,
                navigateToApplications = { navController.navigate(Screens.Applications.route) },
                navigateToAllNotifications = { navController.navigate(Screens.AllNotifications.route) },
                navigateToSettings = { navController.navigate(Screens.Settings.route) },
                navigateToFavorite = { navController.navigate(Screens.Favorite.route) },
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
                modifier = Modifier
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier  = Modifier,
            topBar = {
                TopBarContent(
                    title = "Settings",
                    icon = Icons.Filled.Menu,
                    onNavigationClick = { coroutineScope.launch { drawerState.open() } }
                )
            },
            content = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    item { Spacer(modifier = Modifier.height(64.dp)) }

                    item {
                        ClickableListItem(
                            text = "Automatic deletion of notifications",
                            icon = Icons.Default.Delete,
                            onClick = {
                                openAutoRemoveDialog.value = true
                            }
                        )
                    }
                    item {
                        ClickableListItem(
                            text = "Select apps",
                            icon = Icons.Default.Notifications,
                            onClick = {
                                openSelectAppsDialog.value = true
                            }
                        )
                    }
                    item {
                        ClickableListItem(
                            text = "Private policy",
                            icon = Icons.Default.Lock,
                            onClick = {
                                openPrivatePolicyDialog.value = true
                            }
                        )
                    }
                    item {
                        ClickableListItem(
                            text = "Feedback",
                            icon = Icons.Default.Notifications,
                            onClick = {
                                openFeedbackDialog.value = true
                            }
                        )
                    }
                    item {
                        ClickableListItem(
                            text = "About us",
                            icon = Icons.Default.Info,
                            onClick = {
                                openAboutUsDialog.value = true
                            }
                        )
                    }
                }
                when {
                    openAutoRemoveDialog.value -> openAutoRemoveDialog.value = autoRemoveDialog(
                        settingsViewModel = settingsViewModel,
                        onDismiss = { showAutoDeleteDialog.value = false },
                        onTimeSelected = { selectedTime = it })
                    openSelectAppsDialog.value -> openSelectAppsDialog.value = selectAppsDialog()
                    openPrivatePolicyDialog.value -> openPrivatePolicyDialog.value = privatePolicyDialog()
                    openFeedbackDialog.value -> openFeedbackDialog.value = feedbackDialog()
                    openAboutUsDialog.value -> openAboutUsDialog.value = aboutUsDialog()
                }
            }
        )
    }
}