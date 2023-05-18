package com.sharipov.mynotificationmanager.viewmodel

import androidx.lifecycle.ViewModel
import com.sharipov.mynotificationmanager.data.repository.AppSettingsRepository
import com.sharipov.mynotificationmanager.data.repository.ExcludedAppRepository
import com.sharipov.mynotificationmanager.model.AppSettingsEntity
import com.sharipov.mynotificationmanager.model.ExcludedAppEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface SettingsViewModelAbstract {

    suspend fun saveAppSettings(settings: AppSettingsEntity)

    suspend fun getAppSettings() : AppSettingsEntity?

    suspend fun updateSettings(settings: AppSettingsEntity)

    suspend fun addExcludedApp(app: ExcludedAppEntity)

    suspend fun getAllExcludedApps() : List<ExcludedAppEntity>

    suspend fun removeExcludedApp(packageName: String)

    suspend fun checkExcludedAppExists(packageName: String): Boolean
}


@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val excludedAppRepository: ExcludedAppRepository
): ViewModel(), SettingsViewModelAbstract  {

    override suspend fun saveAppSettings(settings: AppSettingsEntity) {
        appSettingsRepository.saveAppSettings(settings)
    }

    override suspend fun getAppSettings(): AppSettingsEntity? {
        return appSettingsRepository.getAppSettings()
    }

    override suspend fun updateSettings(settings: AppSettingsEntity) {
        appSettingsRepository.updateSettings(settings)
    }

    override suspend fun addExcludedApp(app: ExcludedAppEntity) {
        excludedAppRepository.addExcludedApp(app)
    }

    override suspend fun getAllExcludedApps(): List<ExcludedAppEntity> {
        return excludedAppRepository.getAllExcludedApps()
    }

    override suspend fun removeExcludedApp(packageName: String) {
        excludedAppRepository.removeExcludedApp(packageName)
    }

    override suspend fun checkExcludedAppExists(packageName: String): Boolean {
        val excludedApp = excludedAppRepository.getExcludedAppByPackageName(packageName)
        return excludedApp != null
    }
}