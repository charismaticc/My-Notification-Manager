package com.sharipov.mynotificationmanager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.sharipov.mynotificationmanager.core.navigation.bottomNavBar.BottomNavigationBar
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.allNotificationsGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.applicationsGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.favoriteGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.mainGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.notificationFilterGraph
import com.sharipov.mynotificationmanager.core.navigation.navGraphs.settingsGraph
import com.sharipov.mynotificationmanager.ui.theme.MyNotificationManagerTheme
import com.sharipov.mynotificationmanager.ui.theme.getThemeMode
import com.sharipov.mynotificationmanager.utils.setLocaleBasedOnUserPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var appUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.FLEXIBLE
    private lateinit var someActivityResultLauncher: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        someActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data
            } else {
                println("Something went wrong...")
            }
        }

        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        appUpdateManager.registerListener(installStateUpdateListener)
        checkForAppUpdate()
        setLocaleBasedOnUserPreferences(this)

        setContent {
            val theme = getThemeMode(this)
            MyNotificationManagerTheme(theme) {
                AppContext()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun AppContext() {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = "main_graph",
            ) {
                mainGraph(navController = navController)
                favoriteGraph(navController = navController)
                applicationsGraph(navController = navController)
                allNotificationsGraph(navController = navController)
                notificationFilterGraph(navController = navController)
                settingsGraph(navController = navController)
            }
        }
    }

    private fun checkForAppUpdate() {
        val appUpdateOptions = AppUpdateOptions.defaultOptions(updateType)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = when (updateType) {
                AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                else -> false
            }
            if (isUpdateAllowed && isUpdateAvailable) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        this,
                        appUpdateOptions,
                        123
                    )
                } catch (e: IntentSender.SendIntentException) {
                    // Handle the exception
                }
            }
        }
    }

    private val installStateUpdateListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            Toast.makeText(
                applicationContext,
                "Download successfully. Restarting app in 5 seconds...",
                Toast.LENGTH_LONG
            ).show()
            val job = lifecycleScope.launch {
                delay(5.seconds)
                appUpdateManager.completeUpdate()
            }
            job.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        val appUpdateOptions = AppUpdateOptions.defaultOptions(updateType)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        this,
                        appUpdateOptions,
                        123
                    )
                } catch (e: IntentSender.SendIntentException) {
                    // Handle the exception
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener(installStateUpdateListener)
    }
}
