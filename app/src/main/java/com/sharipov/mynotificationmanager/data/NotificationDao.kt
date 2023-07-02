package com.sharipov.mynotificationmanager.data

import androidx.room.*
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    // Get all notifications in reverse order
    @Query("SELECT * FROM notification ORDER BY id DESC")
    fun getAllFlow(): Flow<List<NotificationEntity>>

    // Get all notifications for a specific user and package in reverse order
    @Query("SELECT * FROM notification WHERE user = :user AND packageName = :packageName ORDER BY id DESC")
    fun getAllUserNotifications(user: String, packageName: String): Flow<List<NotificationEntity>>

    // Search for notifications by text for a specific user and package in reverse order
    @Query(
        "SELECT * FROM notification WHERE user = :user " +
                "AND packageName = :packageName " +
                "AND text LIKE '%' || :query || '%' " +
                "ORDER BY id DESC"
    )
    fun searchUserNotifications(user: String, packageName: String, query: String): Flow<List<NotificationEntity>>

    // Search for notifications by text, user, or package name
    @Query(
        "SELECT * FROM notification WHERE " +
                "text LIKE '%' || :query || '%' OR " +
                "user LIKE '%' || :query || '%' OR " +
                "packageName LIKE '%' || :query || '%'"
    )
    fun searchNotifications(query: String): Flow<List<NotificationEntity>>

    // Get all favorite notifications
    @Query("SELECT * FROM notification WHERE favorite = 1")
    fun getFavoriteNotifications(): Flow<List<NotificationEntity>>

    // Get all distinct application package names
    @Query("SELECT DISTINCT packageName FROM notification")
    fun getApplications(): Flow<List<String>>

    // Get all notifications for a specific application package
    @Query("SELECT DISTINCT user FROM notification WHERE packageName = :packageName")
    fun getApplicationUserNames(packageName: String): Flow<List<String>>

    // Check if a notification already exists for a specific user, text, package name, and app name
    @Query(
        "SELECT COUNT(*) FROM notification WHERE user = :user " +
                "AND text = :text " +
                "AND packageName = :packageName " +
                "AND appName = :appName"
    )
    suspend fun checkNotificationExists(user: String, text: String, packageName: String, appName: String): Int

    // Delete all notifications for a specific user and package
    @Query("DELETE FROM notification WHERE user = :user AND packageName = :packageName")
    suspend fun deleteNotificationsForUser(user: String, packageName: String)

    // Auto-delete notifications older than time limit
    @Query("DELETE FROM notification WHERE time < :autoDeleteTimeout")
    suspend fun deleteExpiredNotification(autoDeleteTimeout: Long)

    // Insert a new notification
    @Insert
    suspend fun insert(notification: NotificationEntity)

    // Update an existing notification
    @Update
    suspend fun update(notification: NotificationEntity)

    // Delete a notification
    @Delete
    suspend fun delete(notification: NotificationEntity)
}