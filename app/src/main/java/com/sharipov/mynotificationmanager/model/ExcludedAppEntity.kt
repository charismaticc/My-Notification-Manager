package com.sharipov.mynotificationmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "excluded_apps",
    indices = [Index(value = ["packageName"], unique = true)])
data class ExcludedAppEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String,
    val appName: String,
    @ColumnInfo(defaultValue = "0")
    val isExcluded: Boolean = false,
    @ColumnInfo(defaultValue = "0")
    val isBlocked: Boolean = false
)