package com.practicum.playlistmaker3.settings.data

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App(var darkTheme: Boolean = false) : Application() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate() {
        super.onCreate()

        switchTheme(darkTheme)
    }

    private fun switchTheme(themeEnable: Boolean) {
        val sharedPrefSaveTheme = getSharedPreferences(NIGHT_THEME, MODE_PRIVATE)
        val switch = (sharedPrefSaveTheme.getBoolean(NIGHT, false))
        if (switch != themeEnable) {
            if (switch) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}