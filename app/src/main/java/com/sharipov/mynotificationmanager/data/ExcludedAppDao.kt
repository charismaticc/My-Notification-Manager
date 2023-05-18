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
    suspend fun addExcludedApp(app: ExcludedAppEntity)

    @Delete
    suspend fun removeExcludedApp(app: ExcludedAppEntity)

    @Query("SELECT * FROM excluded_apps")
    suspend fun getAllExcludedApps(): List<ExcludedAppEntity>
}