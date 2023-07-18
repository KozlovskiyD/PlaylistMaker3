package com.practicum.playlistmaker3.settings.data

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker3.settings.domain.SettingRepository

const val NIGHT_THEME = "night_theme"
const val NIGHT = "night"

class SaveThemeNightImpl(context: Context) : SettingRepository {

    private val sharedPrefSaveTheme =
        context.getSharedPreferences(NIGHT_THEME, Context.MODE_PRIVATE)

    override fun themeNight(checked: Boolean) {
        sharedPrefSaveTheme.edit().putBoolean(NIGHT, checked).apply()
        if (checked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun loadTheme(): Boolean {
        return sharedPrefSaveTheme.getBoolean(NIGHT, false)
    }

    override fun setTheme() {
        val darkTheme: Boolean = sharedPrefSaveTheme.getBoolean(NIGHT, false)
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}