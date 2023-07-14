package com.sharipov.mynotificationmanager.data

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREFS_NAME = "MyAppPreferences"
    private const val KEY_SELECTED_LANGUAGE = "selectedLanguage"
    private const val KEY_BLOCK_NOTIFICATIONS = "blockNotifications"
    private const val KEY_THEME_STYLE = "themeStyle"
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveSelectedLanguage(context: Context, language: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_SELECTED_LANGUAGE, language)
        editor.apply()
    }

    fun getSelectedLanguage(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_SELECTED_LANGUAGE, null)
    }

    fun updateBlockNotificationStatus(context: Context, status: Boolean) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_BLOCK_NOTIFICATIONS, status)
        editor.apply()
    }

    fun getBlockNotification(context: Context): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getBoolean(KEY_BLOCK_NOTIFICATIONS, false)
    }

    fun updateThemeStyle(context: Context, status: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_THEME_STYLE, status)
        editor.apply()
    }

    fun getThemeStyle(context: Context): String {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_THEME_STYLE, "system_theme").toString()
    }
}
