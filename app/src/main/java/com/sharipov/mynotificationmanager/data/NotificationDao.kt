package com.sharipov.mynotificationmanager.data

import androidx.room.*
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("select * from notification ORDER BY id DESC")
    fun getAllFlow(): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM notification WHERE user = :user AND text = :text AND title = :title")
    suspend fun checkNotificationExists(user: String, text: String, title: String): Int

    @Insert
    suspend fun insert(notification: NotificationEntity)

    @Update
    suspend fun update(notification: NotificationEntity)

    @Delete
    suspend fun delete(notification: NotificationEntity)

}