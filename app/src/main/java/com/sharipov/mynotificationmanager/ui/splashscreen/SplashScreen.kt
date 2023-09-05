package com.sharipov.mynotificationmanager.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay
import com.sharipov.mynotificationmanager.utils.TransparentSystemBars
import com.sharipov.mynotificationmanager.utils.UpdateApplicationList
import com.sharipov.mynotificationmanager.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.airbnb.lottie.compose.*
import com.sharipov.mynotificationmanager.data.PreferencesManager

@Composable
fun SplashScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current
    var startAnimate by remember { mutableStateOf(false) }
    UpdateApplicationList(settingsViewModel = settingsViewModel)
    TransparentSystemBars()

    LaunchedEffect(key1 = true) {
        startAnimate = true
        delay(4000)

        val autoDeleteTimeout = settingsViewModel.getAppSettings()?.autoDeleteTimeoutLong ?: 0L

        if (autoDeleteTimeout != 0L) {
            val currentTime = System.currentTimeMillis()
            val deleteThreshold = currentTime - autoDeleteTimeout
            CoroutineScope(Dispatchers.IO).launch {
                homeViewModel.deleteExpiredNotification(deleteThreshold)
            }
        }

        navController.navigate(Screens.AllNotifications.route)
    }

    Splash(context)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Splash(context: Context) {
    val useDarkAnimation = if(PreferencesManager.getThemeStyle(context) == "light_theme") true
    else if (PreferencesManager.getThemeStyle(context) == "dark_theme") false
    else !isSystemInDarkTheme()

    val animation = if(!useDarkAnimation)LottieCompositionSpec.RawRes(resId = R.raw.animation_dark)
    else LottieCompositionSpec.RawRes(resId = R.raw.animation_light)

    val composition by rememberLottieComposition(
        spec = animation
    )
    val isPlaying by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val sizeModifier = Modifier.size(300.dp)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = sizeModifier,
                composition = composition,
                isPlaying = isPlaying,
                restartOnPlay = true
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
