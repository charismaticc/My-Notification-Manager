package com.sharipov.mynotificationmanager.data.repository

import com.sharipov.mynotificationmanager.data.ExcludedAppDao
import com.sharipov.mynotificationmanager.model.ExcludedAppEntity

class ExcludedAppRepository(
    private val excludedApp: ExcludedAppDao
) {
    suspend fun addExcludedApp(app: ExcludedAppEntity) =
        excludedApp.addExcludedApp(excludedApp = app)

    suspend fun getAllExcludedApps() =
        excludedApp.getAllExcludedApps()

    suspend fun removeExcludedApp(packageName: String) =
        excludedApp.removeExcludedApp(packageName = packageName)

    suspend fun getExcludedAppByPackageName(packageName: String) =
        excludedApp.getExcludedAppByPackageName(packageName = packageName)
}