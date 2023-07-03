package com.sharipov.mynotificationmanager.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sharipov.mynotificationmanager.model.AppSettingsEntity
import com.sharipov.mynotificationmanager.model.ExcludedAppEntity
import com.sharipov.mynotificationmanager.model.NotificationEntity


@Database(
    version = 2,
    entities = [
        NotificationEntity::class,
        AppSettingsEntity::class,
        ExcludedAppEntity::class
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true
)
abstract class AppDatabase :RoomDatabase() {

    abstract fun notificationDao(): NotificationDao
    abstract fun appSettingsDao(): AppSettingsDao
    abstract fun excludedAppDao(): ExcludedAppDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "notification.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}