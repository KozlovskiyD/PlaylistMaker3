package com.practicum.playlistmaker3.settings.data

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val NIGHT = "night"

class SaveThemeNight(private val sharedPrefs: SharedPreferences) {

    fun themeNight(checked: Boolean) {
        sharedPrefs.edit().putBoolean(NIGHT, checked).apply()
        if (checked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun loadTheme(): Boolean {
        return sharedPrefs.getBoolean(NIGHT, false)
    }

    fun setTheme() {
        val darkTheme: Boolean = sharedPrefs.getBoolean(NIGHT, false)
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}