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
import com.airbnb.lottie.compose.*
import com.sharipov.mynotificationmanager.R
import com.sharipov.mynotificationmanager.core.navigation.routes.Routes
import com.sharipov.mynotificationmanager.data.ThemePreferences
import com.sharipov.mynotificationmanager.utils.TransparentSystemBars
import com.sharipov.mynotificationmanager.utils.UpdateApplicationList
import com.sharipov.mynotificationmanager.viewmodel.HomeViewModel
import com.sharipov.mynotificationmanager.viewmodel.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel
) {


    UpdateApplicationList(settingsViewModel = settingsViewModel)
    TransparentSystemBars()

    navController.navigate(Routes.PermissionsRoute.route)
}

