package com.sharipov.mynotificationmanager.data.repository

import com.sharipov.mynotificationmanager.data.NotificationDao
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

class NotificationRepository(
    private val notificationDao: NotificationDao
) {

    fun getAllFlow(): Flow<List<NotificationEntity>> = notificationDao.getAllFlow()
    fun insert(notification: NotificationEntity) = notificationDao.insert(notification)
    fun upgrade(notification: NotificationEntity) = notificationDao.update(notification)
    fun delete(notification: NotificationEntity) = notificationDao.delete(notification)

}