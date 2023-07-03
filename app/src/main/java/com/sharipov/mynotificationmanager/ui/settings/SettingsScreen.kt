package com.sharipov.mynotificationmanager.ui.settings


import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharipov.mynotificationmanager.data.PreferencesManager
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.ui.drawer.AppDrawer
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.ui.topbarscomponent.TopBarContent
import com.sharipov.mynotificationmanager.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.sharipov.mynotificationmanager.ui.settings.components.ClickableListItem
import com.sharipov.mynotificationmanager.ui.settings.components.LanguageDialog
import com.sharipov.mynotificationmanager.ui.settings.components.aboutUsDialog
import com.sharipov.mynotificationmanager.ui.settings.components.autoRemoveDialog
import com.sharipov.mynotificationmanager.ui.settings.components.feedbackDialog
import com.sharipov.mynotificationmanager.ui.settings.components.privatePolicyDialog
import com.sharipov.mynotificationmanager.utils.TransparentSystemBars
import com.sharipov.mynotificationmanager.utils.setLanguage
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
    val openPrivatePolicyDialog = remember { mutableStateOf(false) }
    val openFeedbackDialog = remember { mutableStateOf(false) }
    val openAboutUsDialog = remember { mutableStateOf(false) }
    val openLanguageDialog = remember { mutableStateOf(false) }
//    val openThemeDialog = remember { mutableStateOf(false) }

    var selectedTime by remember { mutableStateOf("Never") }
    val showAutoDeleteDialog = remember { mutableStateOf(false) }

    TransparentSystemBars()

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                route = currentRoute,
                navigateToApplications = { navController.navigate(Screens.Applications.route) },
                navigateToAllNotifications = { navController.navigate(Screens.AllNotifications.route) },
                navigateToSettings = { navController.navigate(Screens.Settings.route) },
                navigateToFavorite = { navController.navigate(Screens.Favorite.route) },
                navigateToNotificationManagement = { navController.navigate(Screens.NotificationManagement.route) },
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
                modifier = Modifier
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier,
            topBar = {
                TopBarContent(
                    title = stringResource(id = R.string.settings),
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
                            text = stringResource(id = R.string.automatic_deletion_of_notifications),
                            icon = painterResource(id = R.drawable.ic_auto_delete),
                            onClick = {
                                openAutoRemoveDialog.value = true
                            }
                        )
                    }
//                    item {
//                        ClickableListItem(
//                            text = stringResource(id = R.string.select_theme),
//                            icon = painterResource(id = R.drawable.ic_dark_mode),
//                            onClick = {
//                                openThemeDialog.value = true
//                            }
//                        )
//                    }
                    item {
                        ClickableListItem(
                            text = stringResource(id = R.string.select_language),
                            icon = painterResource(id = R.drawable.ic_language),
                            onClick = {
                                openLanguageDialog.value = true
                            }
                        )
                    }
                    item {
                        ClickableListItem(
                            text = stringResource(id = R.string.private_policy),
                            icon = painterResource(id = R.drawable.ic_policy),
                            onClick = {
                                openPrivatePolicyDialog.value = true
                            }
                        )
                    }
                    item {
                        ClickableListItem(
                            text = stringResource(id = R.string.feedback),
                            icon = painterResource(id = R.drawable.ic_comment),
                            onClick = {
                                openFeedbackDialog.value = true
                            }
                        )
                    }
                    item {
                        ClickableListItem(
                            text = stringResource(id = R.string.about_us),
                            icon = painterResource(id = R.drawable.ic_about),
                            onClick = {
                                openAboutUsDialog.value = true
                            }
                        )
                    }
                }
                val context = LocalContext.current
                when {
                    openPrivatePolicyDialog.value -> openPrivatePolicyDialog.value = privatePolicyDialog()
                    openFeedbackDialog.value -> openFeedbackDialog.value = feedbackDialog()
                    openAboutUsDialog.value -> openAboutUsDialog.value = aboutUsDialog()
                    openAutoRemoveDialog.value -> openAutoRemoveDialog.value = autoRemoveDialog(
                        settingsViewModel = settingsViewModel,
                        onDismiss = { showAutoDeleteDialog.value = false },
                        onTimeSelected = { selectedTime = it }
                    )
                    openLanguageDialog.value -> LanguageDialog(
                        onLanguageSelected = { selectedLanguage ->
                            PreferencesManager.saveSelectedLanguage(context, selectedLanguage)
                            setLanguage(context)
                            if (context is Activity) {
                                context.recreate()
                            }
                        },
                        onDismiss = {
                            openLanguageDialog.value = false
                        }
                    )
//                    openThemeDialog.value -> selectThemeDialog(
//                        onThemeSelected = { selectThemeDialog ->
//                            PreferencesManager.updateThemeStyle(context, selectThemeDialog)
//                            setTheme(context)
//                        },
//                        onDismiss = {
//                            openThemeDialog.value = false
//                        }
//                    )
                }
            }
        )
    }
}

//fun setTheme(context: Context) {
//    val selectedTheme = when (PreferencesManager.getThemeStyle(context)) {
//        "System", "Системный" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
//        "Dark theme", "Тёмная тема" -> AppCompatDelegate.MODE_NIGHT_YES
//        "Light theme", "Светлая тема" -> AppCompatDelegate.MODE_NIGHT_NO
//        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
//    }
//    AppCompatDelegate.setDefaultNightMode(selectedTheme)
//}