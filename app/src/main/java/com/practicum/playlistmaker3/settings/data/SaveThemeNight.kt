package com.practicum.playlistmaker3.settings.data

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

const val NIGHT_THEME = "night_theme"
const val NIGHT = "night"

class SaveThemeNight(context: Context) {

    private val sharedPrefSaveTheme =
        context.getSharedPreferences(NIGHT_THEME, Context.MODE_PRIVATE)

    fun themeNight(checked: Boolean) {
        sharedPrefSaveTheme.edit().putBoolean(NIGHT, checked).apply()
        if (checked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun loadTheme(): Boolean {
        return sharedPrefSaveTheme.getBoolean(NIGHT, false)
    }

    fun setTheme() {
        val darkTheme: Boolean = sharedPrefSaveTheme.getBoolean(NIGHT, false)
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}