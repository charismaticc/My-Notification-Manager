package com.sharipov.mynotificationmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notification")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val user: String,
    val text: String,
    val time: Long
)