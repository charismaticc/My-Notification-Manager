package com.sharipov.mynotificationmanager.data

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREFS_NAME = "MyAppPreferences"
    private const val KEY_SELECTED_LANGUAGE = "selectedLanguage"

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
}
