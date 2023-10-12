package com.sharipov.mynotificationmanager.ui.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.sharipov.mynotificationmanager.data.ThemePreferences

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun getTheme(): Theme? {
    val context = LocalContext.current
    val selectedTheme = ThemePreferences.getTheme(context)
    return selectedTheme?.let { AppThemes.getTheme(it) }
}

@Composable
fun MyNotificationManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val theme = getTheme()
    val isSystemTheme = theme?.name == null

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val dynamicScheme = if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            if(!isSystemTheme) {
                if (darkTheme) {
                    dynamicScheme.copy(
                        primary = theme?.primary ?: MaterialTheme.colorScheme.primary,
                        inversePrimary = theme?.inversePrimary
                            ?: MaterialTheme.colorScheme.inversePrimary
                    )
                } else {
                    dynamicScheme.copy(
                        primary = theme?.primary ?: MaterialTheme.colorScheme.primary,
                        inversePrimary = theme?.inversePrimary
                            ?: MaterialTheme.colorScheme.inversePrimary,
                        background = theme?.background  ?: MaterialTheme.colorScheme.background
                    )
                }
            }
            else {
                dynamicScheme
            }
        }
        darkTheme -> DarkColorScheme.copy(
            primary = theme?.primary ?: MaterialTheme.colorScheme.primary,
            inversePrimary = theme?.inversePrimary ?: MaterialTheme.colorScheme.inversePrimary
        )
        else -> LightColorScheme.copy(
            primary = theme?.primary ?: MaterialTheme.colorScheme.primary,
            background = theme?.background ?: MaterialTheme.colorScheme.background,
            inversePrimary = theme?.inversePrimary ?: MaterialTheme.colorScheme.inversePrimary
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarColorScheme(): TopAppBarColors {
    val topAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = contentColorFor(MaterialTheme.colorScheme.primary),
        actionIconContentColor = contentColorFor(MaterialTheme.colorScheme.primary),
        navigationIconContentColor = contentColorFor(MaterialTheme.colorScheme.primary),
    )

    return if (ThemePreferences.getThemeMode(context = LocalContext.current) == "light_theme"
        || (!isSystemInDarkTheme()
                && ThemePreferences.getThemeMode(context = LocalContext.current) == "system_theme")
    ) topAppBarColors
    else TopAppBarDefaults.centerAlignedTopAppBarColors()
}

data class Theme(val name: String, val primary: Color, val background: Color, val inversePrimary: Color)

object AppThemes {
    private val oceanBreeze = Theme(
        name = "Ocean Breeze",
        primary = Color(0xFF006994),
        background = Color(0xFFC1E1E3),
        inversePrimary = Color(0xFF66A6BA)
    )

    private val caramelDream = Theme(
        name = "Caramel Dream",
        primary = Color(0xFFB6410F),
        background = Color(0xFFF1DAAE),
        inversePrimary = Color(0xFF91553D)
    )

    private val forestHarmony = Theme(
        name = "Forest Harmony",
        primary = Color(0xFF777E5C),
        background = Color(0xFFF2F1EF),
        inversePrimary = Color(0xFFAAB68B)
    )

    private val silverDriftingClouds = Theme(
        name = "Silver Drifting Clouds",
        primary = Color(0xFF586E8F),
        background = Color(0xFFEBEAEB),
        inversePrimary = Color(0xFFB1C7DE)
    )

    private val sunsetSerenity = Theme(
        name = "Sunset Serenity",
        primary = Color(0xFFD66D41),
        background = Color(0xFFF2E3D8),
        inversePrimary = Color(0xFFA96B57)
    )

    private val violetTwilight = Theme(
        name = "Violet Twilight",
        primary = Color(0xFF7B5094),
        background = Color(0xFFE2D5E7),
        inversePrimary = Color(0xFFA88BBE)
    )

    private val fireRedBlaze = Theme(
        name = "Fire Red Blaze",
        primary = Color(0xFFD32F2F),
        background = Color(0xFFFDF5E6),
        inversePrimary = Color(0xFFFFA07A)
    )

    private val sunnyLemonade = Theme(
        name = "Sunny Lemonade",
        primary = Color(0xFFFFD700),
        background = Color(0xFFFFFACD),
        inversePrimary = Color(0xFF808000)
    )


    private val royalPurpleReign = Theme(
        name = "Royal Purple Reign",
        primary = Color(0xFF800080),
        background = Color(0xFFD8BFD8),
        inversePrimary = Color(0xFF4B0082)
    )


    private val earthlyTerracotta = Theme(
        name = "Earthly Terracotta",
        primary = Color(0xFFCC5533),
        background = Color(0xFFF4A460),
        inversePrimary = Color(0xFF8B4513)
    )


    private val themeList = listOf(oceanBreeze, caramelDream, forestHarmony, silverDriftingClouds,
        sunsetSerenity, violetTwilight, fireRedBlaze, sunnyLemonade, royalPurpleReign,
        earthlyTerracotta)

    fun getThemesList(): List<Theme> {
        return themeList
    }

    fun getTheme(name: String): Theme? {
        return themeList.find { it.name == name}
    }
}

@Composable
fun getThemeMode(content: Context): Boolean {
    return when (ThemePreferences.getThemeMode(content)) {
        "dark_theme" -> true
        "light_theme" -> false
        "system_theme" -> isSystemInDarkTheme()
        else -> isSystemInDarkTheme()
    }
}
