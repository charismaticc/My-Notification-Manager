package com.sharipov.mynotificationmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val appName: String,
    val packageName: String,
    val user: String,
    val text: String,
    val time: Long,
    val favorite: Boolean
)