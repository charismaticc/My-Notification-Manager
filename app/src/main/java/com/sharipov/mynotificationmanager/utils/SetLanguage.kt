package com.sharipov.mynotificationmanager.utils

import android.content.Context
import android.content.res.Configuration
import com.sharipov.mynotificationmanager.data.PreferencesManager
import java.util.Locale

fun setLocaleBasedOnUserPreferences(context: Context ) {
    val languageList = listOf("en", "ru", "tg", "uk")
    val language: String
    if(PreferencesManager.getSelectedLanguage(context) == null && getSystemLanguage(context) in languageList)
    {
        language = getSystemLanguage(context)
        PreferencesManager.saveSelectedLanguage(context, language)
    }else {
        language = when (PreferencesManager.getSelectedLanguage(context)) {
            "English" -> "en"
            "Русский" -> "ru"
            "Тоҷикӣ" -> "tg"
            "Українська" -> "uk"
            else -> "en"
        }
    }

    val locale = Locale(language)
    Locale.setDefault(locale)
    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)
    context.createConfigurationContext(configuration)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}

fun getSystemLanguage(context: Context): String {
    return context.resources.configuration.locales.get(0).language
}