package com.sharipov.mynotificationmanager.viewmodel

import androidx.lifecycle.ViewModel
import com.sharipov.mynotificationmanager.data.repository.NotificationRepository
import com.sharipov.mynotificationmanager.model.NotificationEntity
import com.sharipov.mynotificationmanager.model.UserGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeViewModelAbstract {

    val notificationListFlow: Flow<List<NotificationEntity>>
    fun getAllNotificationsInGroup(group: String, packageName: String): Flow<List<NotificationEntity>>
    fun searchNotificationsInGroup(group: String, packageName: String, query: String): Flow<List<NotificationEntity>>
    fun getAllUserNotifications(group: String, userName: String, packageName: String): Flow<List<NotificationEntity>>
    fun searchUserNotifications(group: String, userName: String, packageName: String, query: String): Flow<List<NotificationEntity>>
    fun getApplications(): Flow<List<String>>
    fun getApplicationsNotificationsCount(packageName: String): Flow<Int>
    fun getApplicationUserNames(packageName: String): Flow<List<UserGroup>>
    fun getFavoriteNotifications(): Flow<List<NotificationEntity>>
    fun searchNotifications(query: String): Flow<List<NotificationEntity>>
    fun getNotificationsFromData(fromDate: Long?, toDate: Long?): Flow<List<NotificationEntity>>
    fun addNotification(notification: NotificationEntity)
    fun upgradeNotification(notification: NotificationEntity)
    fun deleteNotification(notification: NotificationEntity)
    fun deleteNotificationsForUser(group: String, user: String, packageName: String)
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

    override fun getAllNotificationsInGroup(group: String, packageName: String) =
        notificationRepository.getAllNotificationsInGroup(group, packageName)

    override fun searchNotificationsInGroup(group: String, packageName: String, query: String) =
        notificationRepository.searchNotificationsInGroup(group, packageName, query)

    override fun getAllUserNotifications(group: String, userName: String, packageName: String) =
        notificationRepository.getAllUserNotifications(group, userName, packageName)

    override fun searchUserNotifications(group: String, userName: String, packageName: String, query: String) =
        notificationRepository.searchUserNotifications(group, userName, packageName, query)

    override fun getApplications() =
        notificationRepository.getApplications()

    override fun getApplicationsNotificationsCount(packageName: String) =
        notificationRepository.getApplicationsNotificationsCount(packageName)

    override fun getFavoriteNotifications() =
        notificationRepository.getFavoriteNotifications()

    override fun searchNotifications(query: String): Flow<List<NotificationEntity>> =
        notificationRepository.searchNotifications(query)

    override fun getNotificationsFromData(fromDate: Long?, toDate: Long?): Flow<List<NotificationEntity>> =
        notificationRepository.getNotificationsFromData(fromDate, toDate)

    override fun getApplicationUserNames(packageName: String) =
        notificationRepository.getApplicationUserNames(packageName)

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

    override fun deleteNotificationsForUser(group: String, user: String, packageName: String) {
        ioScope.launch {
            notificationRepository.deleteNotificationsForUser(group, user, packageName)
        }
    }

    override fun deleteExpiredNotification(autoDeleteTimeout: Long) {
        ioScope.launch {
            notificationRepository.deleteExpiredNotification(autoDeleteTimeout)
        }
    }
}