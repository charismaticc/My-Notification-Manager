package com.sharipov.mynotificationmanager.ui.splashscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
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

@Composable
fun SplashScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel
) {
    var startAnimate by remember { mutableStateOf(false) }
    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimate) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )
    UpdateApplicationList(settingsViewModel = settingsViewModel)
    TransparentSystemBars()

    LaunchedEffect(key1 = true) {
        startAnimate = true
        delay(1500)

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

    Splash(alpha = alphaAnimation)
}

@Composable
fun Splash(alpha: Float) {
    val appIcon = painterResource(R.drawable.ic_app)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = appIcon,
            contentDescription = "App icon",
            modifier = Modifier
                .size(150.dp)
                .alpha(alpha)
                .clip(shape = CircleShape)
        )
    }
}