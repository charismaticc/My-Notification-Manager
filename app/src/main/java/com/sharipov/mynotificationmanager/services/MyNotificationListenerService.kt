package com.sharipov.mynotificationmanager.services

import android.app.Notification
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.sharipov.mynotificationmanager.data.AppDatabase
import com.sharipov.mynotificationmanager.data.NotificationDao
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MyNotificationListenerService : NotificationListenerService() {

    private lateinit var notificationDao: NotificationDao

    override fun onCreate() {
        super.onCreate()
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "notification.db"
        ).build()
        notificationDao = database.notificationDao()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val sbn = sbn.notification
        val extras = sbn.extras
        var user = extras.getString(Notification.EXTRA_TITLE).toString()
        val text = extras.getString(Notification.EXTRA_TEXT).toString()
        val title = sbn.smallIcon.resPackage
        if ("com.instander.android" == title)
        {
            user = user.split(':')[1].trim()
        }
        GlobalScope.launch(Dispatchers.IO) {
            val count = notificationDao.checkNotificationExists(user, text, title)
            if (count == 0) {
                val notificationEntity =
                    NotificationEntity(null, title, user, text, System.currentTimeMillis())
                notificationDao.insert(notificationEntity)
            }
        }
    }
}

