package com.sharipov.mynotificationmanager.services

import android.app.Notification
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.sharipov.mynotificationmanager.data.AppDatabase
import com.sharipov.mynotificationmanager.data.NotificationDao
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MyNotificationListenerService : NotificationListenerService() {

    private lateinit var notificationDao: NotificationDao
    private lateinit var context: Context
    override fun onCreate() {
        super.onCreate()
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "notification.db"
        ).build()
        notificationDao = database.notificationDao()
        context = applicationContext
    }

    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onNotificationPosted(sbn: StatusBarNotification) {

        // get package Name
        val packageName = sbn.packageName

        // get application name
        val pm = context.packageManager
        val appName = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString()


        // get user name
        val extras = sbn.notification.extras
        val user = extras.getString(Notification.EXTRA_TITLE).toString().replace("/", "-")
        // We change '/' to '-' because the tail gives an error indicating the
        // wrong path when switching to a user whose name contains this character

        // get notification text
        val text = extras.getString(Notification.EXTRA_TEXT).toString()



        GlobalScope.launch(Dispatchers.IO) {
            val count = notificationDao.checkNotificationExists(user, text, packageName, appName)
            if (count == 0) {
                val notificationEntity =
                    NotificationEntity(null, appName, packageName, user, text, System.currentTimeMillis(), false)
                notificationDao.insert(notificationEntity)
            }
        }
    }
}

