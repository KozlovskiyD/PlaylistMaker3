package com.practicum.playlistmaker3.settings.data

import com.practicum.playlistmaker3.settings.domain.api.SettingRepository

class SettingRepositoryImpl(private val saveThemeNight: SaveThemeNight) : SettingRepository {

    override fun themeNight(checked: Boolean) {
        saveThemeNight.themeNight(checked)
    }

    override fun loadTheme(): Boolean {
        return saveThemeNight.loadTheme()
    }

    override fun setTheme() {
        saveThemeNight.setTheme()
    }
}