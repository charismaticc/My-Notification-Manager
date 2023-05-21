package com.sharipov.mynotificationmanager.data.repository

import com.sharipov.mynotificationmanager.data.ExcludedAppDao
import com.sharipov.mynotificationmanager.model.ExcludedAppEntity
import kotlinx.coroutines.flow.Flow

class ExcludedAppRepository(
    private val excludedApp: ExcludedAppDao
) {
    fun getAllExcludedApps():  Flow<List<ExcludedAppEntity>> =
        excludedApp.getAllExcludedApps()
    fun searchApplication(query: String): Flow<List<ExcludedAppEntity>> =
        excludedApp.searchApplication(query = query)
    suspend fun addExcludedApp(app: ExcludedAppEntity) =
        excludedApp.addExcludedApp(excludedApp = app)
    suspend fun updateExcludedApp(app: ExcludedAppEntity) =
        excludedApp.updateExcludedApp(excludedApp = app)

}