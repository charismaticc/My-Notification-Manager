package com.sharipov.mynotificationmanager.viewmodel

import androidx.lifecycle.ViewModel
import com.sharipov.mynotificationmanager.data.repository.NotificationRepository
import com.sharipov.mynotificationmanager.model.NotificationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeViewModelAbstract {

    val notificationListFlow: Flow<List<NotificationEntity>>
    fun getAllUserNotifications(userName: String, packageName: String): Flow<List<NotificationEntity>>
    fun searchUserNotifications(userName: String, packageName: String, query: String): Flow<List<NotificationEntity>>
    fun getApplications(): Flow<List<String>>
    fun getApplicationNotifications(packageName: String): Flow<List<NotificationEntity>>
    fun getFavoriteNotifications(): Flow<List<NotificationEntity>>
    fun searchNotifications(query: String): Flow<List<NotificationEntity>>
    fun addNotification(notification: NotificationEntity)
    fun upgradeNotification(notification: NotificationEntity)
    fun deleteNotification(notification: NotificationEntity)
    fun deleteNotificationsForUser(user: String, packageName: String)
    fun deleteExpiredNotification(autoDeleteTimeout: Long)
}

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel(), HomeViewModelAbstract {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override val notificationListFlow: Flow<List<NotificationEntity>> =
        notificationRepository.getAllFlow()

    override fun getAllUserNotifications(userName: String, packageName: String) =
        notificationRepository.getAllUserNotifications(userName, packageName)

    override fun searchUserNotifications(userName: String, packageName: String, query: String) =
        notificationRepository.searchUserNotifications(userName, packageName, query)

    override fun getApplications() =
        notificationRepository.getApplications()

    override fun getFavoriteNotifications() =
        notificationRepository.getFavoriteNotifications()

    override fun searchNotifications(query: String): Flow<List<NotificationEntity>> =
        notificationRepository.searchNotifications(query)

    override fun getApplicationNotifications(packageName: String) =
        notificationRepository.getApplicationNotifications(packageName)

    override fun addNotification(notification: NotificationEntity) {
        ioScope.launch {
            notificationRepository.insert(notification)
        }
    }

    override fun upgradeNotification(notification: NotificationEntity) {
        ioScope.launch {
            notificationRepository.upgrade(notification)
        }
    }

    override fun deleteNotification(notification: NotificationEntity) {
        ioScope.launch {
            notificationRepository.delete(notification)
        }
    }

    override fun deleteNotificationsForUser(user: String, packageName: String) {
        ioScope.launch {
            notificationRepository.deleteNotificationsForUser(user, packageName)
        }
    }

    override fun deleteExpiredNotification(autoDeleteTimeout: Long) {
        ioScope.launch {
            notificationRepository.deleteExpiredNotification(autoDeleteTimeout)
        }
    }
}