package com.practicum.playlistmaker3.settings.domain

class SaveThemeNightUC(private val saveThemeNight: SaveThemeNight) {
    fun saveTheme(checked: Boolean){
        saveThemeNight.themeNight(checked)
    }
    fun loadTheme(): Boolean{
        return saveThemeNight.loadTheme()
    }
}