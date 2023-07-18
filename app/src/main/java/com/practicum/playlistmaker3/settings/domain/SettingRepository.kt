package com.practicum.playlistmaker3.settings.domain

interface SettingRepository {
    fun themeNight(checked: Boolean)
    fun loadTheme(): Boolean
    fun setTheme()
}
