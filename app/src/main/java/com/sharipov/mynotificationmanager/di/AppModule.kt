package com.sharipov.mynotificationmanager.di

import android.app.Application
import com.sharipov.mynotificationmanager.data.AppDatabase
import com.sharipov.mynotificationmanager.data.NotificationDao
import com.sharipov.mynotificationmanager.data.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providerNotificationRepository(
        notificationDao: NotificationDao
    ) : NotificationRepository{
        return NotificationRepository(notificationDao = notificationDao)
    }

    @Singleton
    @Provides
    fun providerNotificationDatabase(app: Application): AppDatabase{
        return AppDatabase.getInstance(context = app)
    }

    @Singleton
    @Provides
    fun providerNotificationDao(appDatabase: AppDatabase): NotificationDao {
        return appDatabase.notificationDao()
    }

}