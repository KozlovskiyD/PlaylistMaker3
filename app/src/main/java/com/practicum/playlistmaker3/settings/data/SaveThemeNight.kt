package com.practicum.playlistmaker3.settings.data

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

@Suppress("UNREACHABLE_CODE")
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
        if (sharedPrefs.contains(NIGHT)) {
            val darkTheme = sharedPrefs.getBoolean(NIGHT, false)
            if (darkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    companion object {
        private const val NIGHT = "night"
    }
}