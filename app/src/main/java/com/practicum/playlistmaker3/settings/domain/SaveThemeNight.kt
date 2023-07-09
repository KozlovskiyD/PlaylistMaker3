package com.practicum.playlistmaker3.settings.domain

interface SaveThemeNight {
    fun themeNight(checked: Boolean)
    fun loadTheme(): Boolean
}
