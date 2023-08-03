package com.sharipov.mynotificationmanager.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.model.NotificationEntity
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun exportDatabase(context: Context, homeViewModel: HomeViewModel): Pair<Boolean, String> {
    if (isExternalStorageWritable()) {
        val exportDir = File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOCUMENTS)
        val notificationManagerDir = File(exportDir, "Notification manager")
        notificationManagerDir.mkdirs()
        val backupDir = File(notificationManagerDir, "backups")
        backupDir.mkdirs()

        val timeStamp = SimpleDateFormat("yyyy.MM.dd_HH.mm.ss", Locale.getDefault()).format(Date())
        val exportFile = File(backupDir, timeStamp + "_backup.json")

        val gson = Gson()
        val notifications = runBlocking {
            homeViewModel.notificationListFlow.first()
        }
        val jsonString = gson.toJson(notifications)

        exportFile.writeText(jsonString)
        Toast.makeText(context, R.string.data_backup_save_in, Toast.LENGTH_SHORT).show()
        return Pair(true, timeStamp)
    } else {
        Toast.makeText(context, R.string.external_storage_not_available, Toast.LENGTH_SHORT).show()
        return Pair(false, "")
    }
}

private fun isExternalStorageWritable(): Boolean {
    val state = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED == state
}


fun importDatabase(context: Context, homeViewModel: HomeViewModel, uri: Uri?): Pair<Boolean, String> {
    val contentResolver: ContentResolver = context.contentResolver
    try {
        val inputStream = uri?.let { contentResolver.openInputStream(it) }
        val jsonString = inputStream?.use { _inputStream ->
            BufferedReader(InputStreamReader(_inputStream)).readText()
        }

        val gson = Gson()
        val listType = object : TypeToken<List<NotificationEntity>>() {}.type
        val notifications: List<NotificationEntity> = gson.fromJson(jsonString, listType)

        runBlocking {
            for (i in notifications) {
                homeViewModel.addNotification(i)
            }
        }
        val status =  context.getString(R.string.notification_imported)
        return Pair(true, status)
    } catch (e: Exception) {
        val status =  context.getString(R.string.error_the_file_is_corrupted)
        return Pair(false, status)
    }
}
