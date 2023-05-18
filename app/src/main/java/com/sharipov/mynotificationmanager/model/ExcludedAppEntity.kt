package com.sharipov.mynotificationmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "excluded_apps")
data class ExcludedAppEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String
)