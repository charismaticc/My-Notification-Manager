package com.sharipov.mynotificationmanager.services

import android.app.Notification
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.sharipov.mynotificationmanager.data.AppDatabase
import com.sharipov.mynotificationmanager.data.ExcludedAppDao
import com.sharipov.mynotificationmanager.data.NotificationDao
import com.sharipov.mynotificationmanager.data.PreferencesManager
import com.sharipov.mynotificationmanager.model.NotificationEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyNotificationListenerService : NotificationListenerService() {

    private lateinit var notificationDao: NotificationDao
    private lateinit var excludedAppDao: ExcludedAppDao
    private lateinit var context: Context
    private val instagramMods = listOf("Instagram", "Instander")
    override fun onCreate() {
        super.onCreate()
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "notification.db"
        ).build()
        notificationDao = database.notificationDao()
        excludedAppDao = database.excludedAppDao()
        context = applicationContext
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        // get package Name
        val packageName = sbn.packageName
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch(Dispatchers.IO) {
            if(PreferencesManager.getBlockNotification(context)) {
                cancelNotification(sbn.key)
            }
        }

        coroutineScope.launch(Dispatchers.IO) {
            if (excludedAppDao.getExcludedAppByPackageName(packageName).isBlocked) {
                cancelNotification(sbn.key)
            }
        }

        coroutineScope.launch(Dispatchers.IO) {
            val excludedApp = excludedAppDao.getExcludedAppByPackageName(packageName)
            if (excludedApp.isExcluded) {
                // get application name
                val pm = context.packageManager
                val appName =
                    pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString()
                // get user name
                val extras = sbn.notification.extras
                var group = extras.getString(Notification.EXTRA_CONVERSATION_TITLE) ?: "not_group"
                var user = extras.getString(Notification.EXTRA_TITLE)?.replace("/", "-") ?: "Unknown"

                if (user.trim() == ""){
                    user = "Unknown"
                }
                // We change '/' to '-' because the tail gives an error indicating the
                // wrong path when switching to a user whose name contains this character

                // Separating the user name from the account name because instagram is shit
                if (appName in instagramMods ) {
                    user = if (user.split(":").size == 2) {
                        user.split(":")[1].trim()
                    } else {
                        user.trim()
                    }

                    if (group != "not_group") {
                        group = group.replace(group.split(" ")[0], "")
                        group = group.trim()
                    }
                } else if(group != "not_group") {
                    user = user.replace("$group:", "").trim()
                }

                if(group == user) {
                    group = "not_group"
                }

                // get notification text
                var text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""
                val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?: ""
               if(text.trim() == "") {
                   text = bigText
               }else if(bigText.trim() != "" && text.trim() != "") {
                   text = bigText
               }

                if (text.isNotEmpty() && group.isNotEmpty()) {
                    val count = notificationDao.checkNotificationExists(user, text, packageName, appName)
                    if(count == 0) {
                        val notificationEntity =
                            NotificationEntity(
                                id = null,
                                appName = appName,
                                packageName = packageName,
                                group = group,
                                user = user,
                                text = text,
                                time = System.currentTimeMillis(),
                                favorite = false
                            )
                        notificationDao.insert(notificationEntity)
                    }
                }
            }
        }
    }
}