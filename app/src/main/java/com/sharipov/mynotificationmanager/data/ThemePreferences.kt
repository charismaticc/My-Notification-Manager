package com.sharipov.mynotificationmanager.data

import android.content.Context
import android.content.SharedPreferences

object ThemePreferences {
    private const val PREFS_NAME = "MyAppThemePreferences"
    private const val KEY_SELECTED_THEME = "selectedTheme"
    private const val KEY_CREATED_THEME = "createdTheme"
    private const val KEY_THEME_MODE = "themeMode"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun updateSelectedTheme(context: Context, color: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_SELECTED_THEME, color)
        editor.apply()
    }

    fun getTheme(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_SELECTED_THEME, "System")
    }

    fun updateCreatedTheme(context: Context, color: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_CREATED_THEME, color)
        editor.apply()
    }

    fun getCreatedTheme(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_CREATED_THEME, null)
    }

    fun updateThemeMode(context: Context, status: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_THEME_MODE, status)
        editor.apply()
    }

    fun getThemeMode(context: Context): String {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_THEME_MODE, "system_theme").toString()
    }
}
