package com.sharipov.mynotificationmanager.data

import androidx.room.*
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notification ORDER BY id DESC")
    fun getAllFlow(): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notification WHERE user = :user AND packageName = :packageName ORDER BY id DESC")
    fun getAllUserNotifications(user: String, packageName: String): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notification WHERE favorite = 1")
    fun getFavoriteNotifications(): Flow<List<NotificationEntity>>
    @Query("SELECT DISTINCT packageName FROM notification")
    fun getApplications(): Flow<List<String>>
    @Query("SELECT * FROM notification WHERE packageName = :packageName")
    fun getApplicationNotifications(packageName: String): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM notification WHERE user = :user AND text = :text AND packageName = :packageName AND appName = :appName")
    suspend fun checkNotificationExists(user: String, text: String, packageName: String, appName: String): Int

    @Query("DELETE FROM notification WHERE user = :user AND packageName = :packageName")
    suspend fun deleteNotificationsForUser(user: String, packageName: String)

    @Insert
    suspend fun insert(notification: NotificationEntity)

    @Update
    suspend fun update(notification: NotificationEntity)

    @Delete
    suspend fun delete(notification: NotificationEntity)

}