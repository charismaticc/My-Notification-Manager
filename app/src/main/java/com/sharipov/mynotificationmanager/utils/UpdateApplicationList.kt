package com.sharipov.mynotificationmanager.utils;

import android.content.pm.PackageManager
import androidx.compose.runtime.Composable;
import androidx.compose.ui.platform.LocalContext
import com.sharipov.mynotificationmanager.model.ExcludedAppEntity
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel
import kotlinx.coroutines.runBlocking


@Composable
fun UpdateApplicationList(settingsViewModel: SettingsViewModel) {
    val packageManager = LocalContext.current.packageManager
    val apps = packageManager.getInstalledPackages(0)

    for (i in apps) {
        val appName =
            packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(i.packageName, PackageManager.GET_META_DATA)
            ).toString()

        val app = ExcludedAppEntity(
            packageName = i.packageName.toString(),
            appName = appName,
            isExcluded = false
        )

        runBlocking {
            settingsViewModel.addExcludedApp(app)
        }
    }
}
