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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.ui.drawer.AppDrawer
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.utils.Constants
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.sharipov.mynotificationmanager.ui.settings.components.ClickableListItem
import com.sharipov.mynotificationmanager.ui.settings.components.aboutUsDialog
import com.sharipov.mynotificationmanager.ui.settings.components.autoRemoveDialog
import com.sharipov.mynotificationmanager.ui.settings.components.feedbackDialog
import com.sharipov.mynotificationmanager.ui.settings.components.privatePolicyDialog
import com.sharipov.mynotificationmanager.ui.settings.components.selectAppsDialog
import com.sharipov.mynotificationmanager.ui.transparentSystemBars.TransparentSystemBars

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Constants.Screens.SETTINGS_SCREEN
    val context = LocalContext.current

    val openAutoRemoveDialog = remember { mutableStateOf(false) }
    val openSelectAppsDialog = remember { mutableStateOf(false) }
    val openPrivatePolicyDialog = remember { mutableStateOf(false) }
    val openFeedbackDialog = remember { mutableStateOf(false) }
    val openAboutUsDialog = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
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
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Settings",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { drawerState.open() }
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
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
                        selectedTime = selectedTime,
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