package com.practicum.playlistmaker3.settings.domain.api

interface SettingIteractor {
    fun themeNight(checked: Boolean)
    fun loadTheme(): Boolean
    fun setTheme()
}