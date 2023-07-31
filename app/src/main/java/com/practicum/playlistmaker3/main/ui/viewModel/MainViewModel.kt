package com.practicum.playlistmaker3.main.ui.viewModel

import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker3.settings.domain.api.SettingIteractor

class MainViewModel(private val settingIteractor: SettingIteractor) :
    ViewModel() {

    fun setTheme() {
        settingIteractor.setTheme()
    }
}