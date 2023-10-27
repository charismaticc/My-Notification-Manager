package com.sharipov.mynotificationmanager.ui.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
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
import androidx.core.view.WindowCompat
import com.sharipov.mynotificationmanager.data.ThemePreferences

private val darkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val lightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun getTheme(): Theme {
    val context = LocalContext.current
    val selectedTheme = ThemePreferences.getTheme(context)
    return selectedTheme.let { AppThemes.getTheme(it) }
}

@Composable
fun MyNotificationManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val theme = getTheme()
    val isSystemTheme = theme.name == "System"

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isSystemTheme) {
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            } else {
                if (darkTheme) theme.darkColorScheme else theme.lightColorScheme
            }
        }

        darkTheme -> theme.darkColorScheme
        else -> theme.lightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            val window = WindowCompat.getInsetsController((view.context as Activity).window, view)
            window.isAppearanceLightStatusBars = darkTheme
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
    return TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = contentColorFor(MaterialTheme.colorScheme.primary),
        actionIconContentColor = contentColorFor(MaterialTheme.colorScheme.primary),
        navigationIconContentColor = contentColorFor(MaterialTheme.colorScheme.primary),
    )
}

data class Theme(
    val name: String,
    val lightColorScheme: ColorScheme,
    val darkColorScheme: ColorScheme
)

object AppThemes {
    private val oceanBreeze =
        Theme("Ocean Breeze", oceanBreezeLightColorScheme, oceanBreezeDarkColorScheme)
    private val caramelDream =
        Theme("Caramel Dream", caramelDreamLightColorScheme, caramelDreamDarkColorScheme)
    private val forestHarmony =
        Theme("Forest Harmony", forestHarmonyLightColorScheme, forestHarmonyDarkColorScheme)

    /*
     silverDriftingClouds, sunsetSerenity, violetTwilight, fireRedBlaze, sunnyLemonade, royalPurpleReign, earthlyTerracotta
     */

    private val themeList = listOf(oceanBreeze, caramelDream, forestHarmony)

    fun getThemesList(): List<Theme> {
        return themeList
    }

    fun getTheme(name: String): Theme {
        return themeList.find { it.name == name } ?: Theme(
            "System",
            lightColorScheme,
            darkColorScheme
        )
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

// "Ocean Breeze"
private val oceanBreezeDarkColorScheme = darkColorScheme(
    primary = Color(0xFF006994),
    inversePrimary = Color(0xFF66A6BA),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF33445A),
    onPrimaryContainer = Color(0xFFFFFFFF),

    secondary = Color(0xFF99004C),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFF006994),
    onSecondaryContainer = Color(0xFFFFFFFF),  // цвет активной иконки ботмБар

    tertiary = Color(0xFF00704A),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFF2D6C50),
    onTertiaryContainer = Color(0xFFFFFFFF),

    background = Color(0xFF042E40),
    onBackground = Color(0xFFFFFFFF),

    surface = Color(0xFF333333),   // фон ботом шилта
    onSurface = Color(0xFFFFFFFF),  // цвет текста на ботм шилте
    surfaceVariant = Color(0xFF666666),  // цвет карточки
    onSurfaceVariant = Color(0xFFFFFFFF),  // цыет текста на карточке

    surfaceTint = Color(0xFF444444),
    inverseSurface = Color(0xFFDDDDDD),
    inverseOnSurface = Color(0xFF333333),

    error = Color(0xFFFF0000),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFF330000),
    onErrorContainer = Color(0xFFFFFFFF),

    outline = Color(0xFF006994),  // рамка радиобатона
    outlineVariant = Color(0xFFFFFFFF),  // цвет внутри радиобатона

    scrim = Color(0xAA000000),  // Затемнения фона от ботомшилта
    surfaceBright = Color(0xFFCCCCCC),
    surfaceContainer = Color(0xFF444444),
    surfaceContainerHigh = Color(0xFF555555),
    surfaceContainerHighest = Color(0xFF666666),
    surfaceContainerLow = Color(0xFF333333),
    surfaceContainerLowest = Color(0xFFFF0000),
    surfaceDim = Color(0xFF555555)
)

private val oceanBreezeLightColorScheme = lightColorScheme(
    primary = Color(0xFF006994),
    inversePrimary = Color(0xFF66A6BA),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFC1E1E3),
    onPrimaryContainer = Color(0xFF000000),

    secondary = Color(0xFF99004C),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFC1E1E3),
    onSecondaryContainer = Color(0xFF006994),  // цвет активной иконки ботмБар

    tertiary = Color(0xFF00704A),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFC1E1E3),
    onTertiaryContainer = Color(0xFF000000),

    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),

    surface = Color(0xFFFFFFFF),  // фон ботом шилта
    onSurface = Color(0xFF000000),  // цвет текста на ботм шилте
    surfaceVariant = Color(0xFFCCCCCC),  // цвет карточки
    onSurfaceVariant = Color(0xFF006994),  // цыет текста на карточке

    surfaceTint = Color(0xFFEEEEEE),
    inverseSurface = Color(0xFF000000),
    inverseOnSurface = Color(0xFFFFFFFF),

    error = Color(0xFFFF0000),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDDDD),
    onErrorContainer = Color(0xFF000000),

    outline = Color(0xFF006994), // рамка радиобатона
    outlineVariant = Color(0xFFFFFFFF), // цвет внутри радиобатона

    scrim = Color(0x00000000),  // Затемнения фона от ботомшилта
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainer = Color(0xFFFFFFFF),
    surfaceContainerHigh = Color(0xFFDDDDDD),
    surfaceContainerHighest = Color(0xFFCCCCCC),
    surfaceContainerLow = Color(0xFFEEEEEE),
    surfaceContainerLowest = Color(0xFFDDDDDD),
    surfaceDim = Color(0xFFDDDDDD)
)

// name = "Caramel Dream",
private val caramelDreamLightColorScheme = lightColorScheme(
    primary = Color(0xFF006994),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF33445A),
    onPrimaryContainer = Color(0xFFFFFFFF),
    inversePrimary = Color(0xFF66A6BA),

    secondary = Color(0xFF99004C),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFF66A6BA),
    onSecondaryContainer = Color(0xFF006994),  // цвет активной иконки ботмБар

    tertiary = Color(0xFF00704A),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFF2D6C50),
    onTertiaryContainer = Color(0xFFFFFFFF),

    background = Color(0xFFC1E1E3),
    onBackground = Color(0xFF000000),

    surface = Color(0xFF333333),    // фон ботом шилта
    onSurface = Color(0xFFFFFFFF),  // цвет текста на ботм шилте
    surfaceVariant = Color(0xFF666666),  // цвет карточки
    onSurfaceVariant = Color(0xFF006994),  // цыет текста на карточке

    surfaceTint = Color(0xFF444444),
    inverseSurface = Color(0xFFDDDDDD),
    inverseOnSurface = Color(0xFF333333),

    error = Color(0xFFFF0000),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFF330000),
    onErrorContainer = Color(0xFFFFFFFF),

    outline = Color(0xFF006994), // рамка радиобатона
    outlineVariant = Color(0xFFCCCCCC), // цвет внутри радиобатона

    scrim = Color(0xAA000000),  // Затемнения фона от ботомшилта
    surfaceBright = Color(0xFFCCCCCC),
    surfaceContainer = Color(0xFF444444),
    surfaceContainerHigh = Color(0xFF555555),
    surfaceContainerHighest = Color(0xFF666666),
    surfaceContainerLow = Color(0xFF333333),
    surfaceContainerLowest = Color(0xFF222222),
    surfaceDim = Color(0xFF555555)
)

private val caramelDreamDarkColorScheme = darkColorScheme(
    primary = Color(0xFFB6410F),
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFFF1DAAE),
    onPrimaryContainer = Color(0xFF000000),

    secondary = Color(0xFF91553D),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFF1DAAE),
    onSecondaryContainer = Color(0xFF000000),  // цвет активной иконки ботмБар

    tertiary = Color(0xFF00704A),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFF1DAAE),
    onTertiaryContainer = Color(0xFF000000),

    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),

    surface = Color(0xFFDDDDDD),  // фон ботом шилта
    onSurface = Color(0xFF000000),  // цвет текста на ботм шилте
    surfaceVariant = Color(0xFFCCCCCC),  // цвет карточки
    onSurfaceVariant = Color(0xFF000000),  // цыет текста на карточке

    surfaceTint = Color(0xFFEEEEEE),
    inverseSurface = Color(0xFF000000),
    inverseOnSurface = Color(0xFFFFFFFF),

    error = Color(0xFFFF0000),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDDDD),
    onErrorContainer = Color(0xFF000000),

    outline = Color(0xFF999999), // рамка радиобатона
    outlineVariant = Color(0xFFCCCCCC), // цвет внутри радиобатона

    scrim = Color(0x00000000),  // Затемнения фона от ботомшилта
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainer = Color(0xFFFFFFFF),
    surfaceContainerHigh = Color(0xFFDDDDDD),
    surfaceContainerHighest = Color(0xFFCCCCCC),
    surfaceContainerLow = Color(0xFFEEEEEE),
    surfaceContainerLowest = Color(0xFFDDDDDD),
    surfaceDim = Color(0xFFDDDDDD)
)


//name = "Forest Harmony",
private val forestHarmonyLightColorScheme = lightColorScheme(
    primary = Color(0xFF777E5C),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF33445A),
    onPrimaryContainer = Color(0xFFFFFFFF),
    inversePrimary = Color(0xFFAAB68B),

    secondary = Color(0xFF99004C),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFF442B64),
    onSecondaryContainer = Color(0xFFFFFFFF),  // цвет активной иконки ботмБар

    tertiary = Color(0xFF00704A),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFF2D6C50),
    onTertiaryContainer = Color(0xFFFFFFFF),

    background = Color(0xFFF2F1EF),
    onBackground = Color(0xFF33445A),

    surface = Color(0xFF333333),  // фон ботом шилта
    onSurface = Color(0xFFFFFFFF),  // цвет текста на ботм шилте
    surfaceVariant = Color(0xFF666666),  // цвет карточки
    onSurfaceVariant = Color(0xFFFFFFFF),  // цыет текста на карточке

    surfaceTint = Color(0xFF444444),
    inverseSurface = Color(0xFFDDDDDD),
    inverseOnSurface = Color(0xFF333333),

    error = Color(0xFFFF0000),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFF330000),
    onErrorContainer = Color(0xFFFFFFFF),

    outline = Color(0xFF777E5C), // рамка радиобатона
    outlineVariant = Color(0xFFCCCCCC), // цвет внутри радиобатона

    scrim = Color(0xAA000000),  // Затемнения фона от ботомшилта
    surfaceBright = Color(0xFFCCCCCC),
    surfaceContainer = Color(0xFF444444),
    surfaceContainerHigh = Color(0xFF555555),
    surfaceContainerHighest = Color(0xFF666666),
    surfaceContainerLow = Color(0xFF333333),
    surfaceContainerLowest = Color(0xFF222222),
    surfaceDim = Color(0xFF555555)
)

private val forestHarmonyDarkColorScheme = darkColorScheme(
    primary = Color(0xFF777E5C),
    onPrimary = Color(0xFFF2F1EF),
    primaryContainer = Color(0xFFF2F1EF),
    onPrimaryContainer = Color(0xFF000000),

    secondary = Color(0xFFAAB68B),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFAAB68B), // Цвет фона активной кнопки
    onSecondaryContainer = Color(0xFFF2F1EF),  // цвет активной иконки ботмБар

    tertiary = Color(0xFF00704A),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFF2F1EF),
    onTertiaryContainer = Color(0xFF000000),

    background = Color(0xFF535841),
    onBackground = Color(0xFFF2F1EF),

    surface = Color(0xFFDDDDDD), // фон ботом шилта
    onSurface = Color(0xFF000000), // цвет текста на ботм шилте
    surfaceVariant = Color(0xFFCCCCCC),  // цвет карточки
    onSurfaceVariant = Color(0xFF000000),  // цыет текста на карточке

    surfaceTint = Color(0xFFEEEEEE),
    inverseSurface = Color(0xFF000000),
    inverseOnSurface = Color(0xFFFFFFFF),

    error = Color(0xFFFF0000),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDDDD),
    onErrorContainer = Color(0xFF000000),

    outline = Color(0xFF999999), // рамка радиобатона
    outlineVariant = Color(0xFFCCCCCC), // цвет внутри радиобатона

    scrim = Color(0x00000000),   // Затемнения фона от ботомшилта
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainer = Color(0xFFFFFFFF),
    surfaceContainerHigh = Color(0xFFDDDDDD),
    surfaceContainerHighest = Color(0xFFCCCCCC),
    surfaceContainerLow = Color(0xFFEEEEEE),
    surfaceContainerLowest = Color(0xFFDDDDDD),
    surfaceDim = Color(0xFFDDDDDD)
)


//private val silverDriftingClouds = Theme(
//    name = "Silver Drifting Clouds",
//    primary = Color(0xFF586E8F),
//    background = Color(0xFFEBEAEB),
//    inversePrimary = Color(0xFFB1C7DE)
//)
//
//private val sunsetSerenity = Theme(
//    name = "Sunset Serenity",
//    primary = Color(0xFFD66D41),
//    background = Color(0xFFF2E3D8),
//    inversePrimary = Color(0xFFA96B57)
//)
//
//private val violetTwilight = Theme(
//    name = "Violet Twilight",
//    primary = Color(0xFF7B5094),
//    background = Color(0xFFE2D5E7),
//    inversePrimary = Color(0xFFA88BBE)
//)
//
//private val fireRedBlaze = Theme(
//    name = "Fire Red Blaze",
//    primary = Color(0xFFD32F2F),
//    background = Color(0xFFFDF5E6),
//    inversePrimary = Color(0xFFFFA07A)
//)
//
//private val sunnyLemonade = Theme(
//    name = "Sunny Lemonade",
//    primary = Color(0xFFFFD700),
//    background = Color(0xFFFFFACD),
//    inversePrimary = Color(0xFF808000)
//)
//
//
//private val royalPurpleReign = Theme(
//    name = "Royal Purple Reign",
//    primary = Color(0xFF800080),
//    background = Color(0xFFD8BFD8),
//    inversePrimary = Color(0xFF4B0082)
//)
//
//
//private val earthlyTerracotta = Theme(
//    name = "Earthly Terracotta",
//    primary = Color(0xFFCC5533),
//    background = Color(0xFFF4A460),
//    inversePrimary = Color(0xFF8B4513)
//)