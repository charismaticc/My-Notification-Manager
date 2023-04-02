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
    fun addNotification(notification: NotificationEntity)
    fun upgradeNotification(notification: NotificationEntity)
    fun deleteNotification(notification: NotificationEntity)
}

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val notificationRepository: NotificationRepository
): ViewModel(), HomeViewModelAbstract {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    override val notificationListFlow: Flow<List<NotificationEntity>> = notificationRepository.getAllFlow()

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

}