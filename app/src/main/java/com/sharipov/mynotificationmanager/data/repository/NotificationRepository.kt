package com.sharipov.mynotificationmanager.data.repository

import com.sharipov.mynotificationmanager.data.NotificationDao
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

class NotificationRepository(
    private val notificationDao: NotificationDao
) {

    fun getAllFlow(): Flow<List<NotificationEntity>> = notificationDao.getAllFlow()
    suspend fun insert(notification: NotificationEntity) = notificationDao.insert(notification)
    suspend fun upgrade(notification: NotificationEntity) = notificationDao.update(notification)
    suspend fun delete(notification: NotificationEntity) = notificationDao.delete(notification)

}