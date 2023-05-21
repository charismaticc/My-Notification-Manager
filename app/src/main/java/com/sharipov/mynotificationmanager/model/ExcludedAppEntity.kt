package com.sharipov.mynotificationmanager.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "excluded_apps",
    indices = [Index(value = ["packageName"], unique = true)])
data class ExcludedAppEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String,
    val appName: String,
    val isExcluded: Boolean = false
)