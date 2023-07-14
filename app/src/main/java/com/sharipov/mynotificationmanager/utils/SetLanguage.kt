package com.sharipov.mynotificationmanager.utils

import android.content.Context
import android.content.res.Configuration
import com.sharipov.mynotificationmanager.data.PreferencesManager
import java.util.Locale

fun setChanges(context: Context ) {
    val language = when (PreferencesManager.getSelectedLanguage(context)) {
        "English" -> "en"
        "Русский" -> "ru"
        "Тоҷикӣ" -> "tg"
        "Українська" -> "uk"
        else -> "en"
    }

    val locale = Locale(language)
    Locale.setDefault(locale)
    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}