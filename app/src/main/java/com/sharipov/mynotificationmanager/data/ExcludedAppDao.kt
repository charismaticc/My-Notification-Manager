package com.sharipov.mynotificationmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sharipov.mynotificationmanager.model.ExcludedAppEntity

@Dao
interface ExcludedAppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExcludedApp(excludedApp: ExcludedAppEntity)

    @Query("DELETE FROM excluded_apps WHERE packageName = :packageName")
    suspend fun removeExcludedApp(packageName: String)

    @Query("SELECT * FROM excluded_apps")
    suspend fun getAllExcludedApps(): List<ExcludedAppEntity>

    @Query("SELECT * FROM excluded_apps WHERE packageName = :packageName")
    suspend fun getExcludedAppByPackageName(packageName: String): ExcludedAppEntity?
}