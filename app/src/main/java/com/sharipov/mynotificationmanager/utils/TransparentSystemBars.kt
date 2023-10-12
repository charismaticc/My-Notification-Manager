package com.sharipov.mynotificationmanager.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sharipov.mynotificationmanager.data.ThemePreferences

@Composable
fun TransparentSystemBars() {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = if(ThemePreferences.getThemeMode(context) == "light_theme") true
                        else if (ThemePreferences.getThemeMode(context) == "dark_theme") false
                        else !isSystemInDarkTheme()
    val systemBarsBackgroundColor = if(useDarkIcons) MaterialTheme.colorScheme.primary
                          else MaterialTheme.colorScheme.background
    val navigationBarsBackgroundColor = MaterialTheme.colorScheme.background
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = systemBarsBackgroundColor,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(navigationBarsBackgroundColor)
    }
}