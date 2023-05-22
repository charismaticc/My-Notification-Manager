package com.sharipov.mynotificationmanager.ui.splashscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharipov.mynotificationmanager.navigation.Screens
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay
import com.sharipov.mynotificationmanager.utils.TransparentSystemBars
import com.sharipov.mynotificationmanager.utils.DeleteExpiredNotifications
import com.sharipov.mynotificationmanager.utils.UpdateApplicationList

@Composable
fun SplashScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel
) {

    var startAnimate by remember {
        mutableStateOf(false)
    }

    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimate) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    UpdateApplicationList(settingsViewModel = settingsViewModel)
    DeleteExpiredNotifications(homeViewModel = homeViewModel, settingsViewModel = settingsViewModel)
    TransparentSystemBars()

    LaunchedEffect(key1 = true) {
        startAnimate = true
        delay(1000)
        navController.navigate(Screens.Applications.route)
    }

    Splash(alpha = alphaAnimation.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha),
            imageVector = Icons.Default.Notifications,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}