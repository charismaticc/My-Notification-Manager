package com.sharipov.mynotificationmanager.data

import androidx.room.*
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("select * from notification")
    fun getAllFlow(): Flow<List<NotificationEntity>>

    @Insert
    fun insert(notification: NotificationEntity)

    @Update
    fun update(notification: NotificationEntity)

    @Delete
    fun delete(notification: NotificationEntity)
}