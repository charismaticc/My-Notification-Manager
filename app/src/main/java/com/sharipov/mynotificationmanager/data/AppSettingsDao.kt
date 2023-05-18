package com.sharipov.mynotificationmanager.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sharipov.mynotificationmanager.model.AppSettingsEntity

@Dao
interface AppSettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAppSettings(settings: AppSettingsEntity)

    @Query("SELECT * FROM app_settings WHERE id = 0")
    suspend fun getAppSettings(): AppSettingsEntity?

    @Update
    suspend fun updateSettings(settings: AppSettingsEntity)
}
