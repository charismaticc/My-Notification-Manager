package com.sharipov.mynotificationmanager

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.compose.rememberNavController
import com.sharipov.mynotificationmanager.ui.theme.MyNotificationManagerTheme
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import com.sharipov.mynotificationmanager.navigation.SetupNavHost
import com.sharipov.mynotificationmanager.utils.setLanguage
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request permission to listen to notifications
        if (!NotificationManagerCompat.getEnabledListenerPackages(this).contains(packageName)) {
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            startActivity(intent)
        }

        setLanguage(this)

        val homeViewModel: HomeViewModel by viewModels()
        val settingsViewModel: SettingsViewModel by viewModels()

        setContent {
            MyNotificationManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SetupNavHost(homeViewModel, settingsViewModel, navController = navController)
                }
            }
        }
    }
}
