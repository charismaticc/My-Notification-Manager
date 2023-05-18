package com.sharipov.mynotificationmanager.data.repository

import com.sharipov.mynotificationmanager.data.AppSettingsDao
import com.sharipov.mynotificationmanager.model.AppSettingsEntity

class AppSettingsRepository (
    private val appSettings: AppSettingsDao
) {

    suspend fun saveAppSettings(settings: AppSettingsEntity) =
        appSettings.saveAppSettings(settings = settings)

    suspend fun getAppSettings() =
        appSettings.getAppSettings()

    suspend fun updateSettings(settings: AppSettingsEntity) =
        appSettings.updateSettings(settings)
}