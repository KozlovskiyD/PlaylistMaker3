package com.practicum.playlistmaker3.settings.domain.impl

import com.practicum.playlistmaker3.settings.domain.api.SettingIteractor
import com.practicum.playlistmaker3.settings.domain.api.SettingRepository

class SettingIteractorImpl(private val settingRepository: SettingRepository) : SettingIteractor {

    override fun themeNight(checked: Boolean) {
        settingRepository.themeNight(checked)
    }

    override fun loadTheme(): Boolean {
        return settingRepository.loadTheme()
    }

    override fun setTheme() {
        settingRepository.setTheme()
    }
}