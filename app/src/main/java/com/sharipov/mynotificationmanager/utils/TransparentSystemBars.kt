package com.sharipov.mynotificationmanager.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun TransparentSystemBars() {
    val systemUiController = rememberSystemUiController()
    val systemBarsBackgroundColor =  MaterialTheme.colorScheme.primary
    val navigationBarsBackgroundColor = MaterialTheme.colorScheme.background
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = systemBarsBackgroundColor,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(navigationBarsBackgroundColor)
    }
}