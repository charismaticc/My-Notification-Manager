package com.sharipov.mynotificationmanager.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun DeleteExpiredNotifications(
    homeViewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel
) {
    var selectedTimeLong = 0L
    runBlocking {
        val appSettings = settingsViewModel.getAppSettings()
        if (appSettings != null) {
            selectedTimeLong = appSettings.autoDeleteTimeoutLong
        }
    }
    if (selectedTimeLong != 0L) {
        for (i in homeViewModel.notificationListFlow.collectAsState(emptyList()).value) {
            if ((System.currentTimeMillis() - i.time) >= selectedTimeLong && !i.favorite) {
                homeViewModel.deleteNotification(i)
            }
        }
    }
}