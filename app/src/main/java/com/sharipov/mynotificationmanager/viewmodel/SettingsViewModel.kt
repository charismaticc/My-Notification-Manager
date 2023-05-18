package com.sharipov.mynotificationmanager.viewmodel

import androidx.lifecycle.ViewModel
import com.sharipov.mynotificationmanager.data.repository.AppSettingsRepository
import com.sharipov.mynotificationmanager.model.AppSettingsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface SettingsViewModelAbstract {

    suspend fun saveAppSettings(settings: AppSettingsEntity)

    suspend fun getAppSettings() : AppSettingsEntity?

    suspend fun updateSettings(settings: AppSettingsEntity)
}


@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
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
}