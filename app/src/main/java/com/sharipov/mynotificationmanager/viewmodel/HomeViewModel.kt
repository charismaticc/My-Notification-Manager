package com.sharipov.mynotificationmanager.viewmodel

import androidx.lifecycle.ViewModel
import com.sharipov.mynotificationmanager.data.repository.NotificationRepository
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface HomeViewModelAbstract {

    val notificationListFlow: Flow<List<NotificationEntity>>
    fun addNotification(notification: NotificationEntity)
    fun upgradeNotification(notification: NotificationEntity)
    fun deleteNotification(notification: NotificationEntity)
}

class HomeViewModel constructor(
    private val notificationRepository: NotificationRepository
): ViewModel(), HomeViewModelAbstract {

    override val notificationListFlow: Flow<List<NotificationEntity>> = notificationRepository.getAllFlow()

    override fun addNotification(notification: NotificationEntity) = notificationRepository.insert(notification)

    override fun upgradeNotification(notification: NotificationEntity) = notificationRepository.upgrade(notification)

    override fun deleteNotification(notification: NotificationEntity) = notificationRepository.delete(notification)

}