package com.sharipov.mynotificationmanager.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sharipov.mynotificationmanager.data.PreferencesManager

@Composable
fun TransparentSystemBars() {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = if(PreferencesManager.getThemeStyle(context) == "light_theme") true
                        else if (PreferencesManager.getThemeStyle(context) == "dark_theme") false
                        else !isSystemInDarkTheme()
    val backgroundColor = MaterialTheme.colorScheme.background
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = backgroundColor,
            darkIcons = useDarkIcons
        )
    }
}