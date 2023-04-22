package com.sharipov.mynotificationmanager.data

import androidx.room.*
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("select * from notification ORDER BY id DESC")
    fun getAllFlow(): Flow<List<NotificationEntity>>

    @Query("select * from notification WHERE user = :user ORDER BY id DESC")
    fun getAllUserNotifications(user: String): Flow<List<NotificationEntity>>

    @Query("select * from notification WHERE favorite = 1")
    fun getFavoriteNotifications(): Flow<List<NotificationEntity>>
    @Query("SELECT COUNT(*) FROM notification WHERE user = :user AND text = :text AND packageName = :packageName AND appName = :appName")
    suspend fun checkNotificationExists(user: String, text: String, packageName: String, appName: String): Int

    @Insert
    suspend fun insert(notification: NotificationEntity)

    @Update
    suspend fun update(notification: NotificationEntity)

    @Delete
    suspend fun delete(notification: NotificationEntity)

}