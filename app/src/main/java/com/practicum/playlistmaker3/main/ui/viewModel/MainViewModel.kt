package com.practicum.playlistmaker3.main.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker3.settings.domain.api.SettingIteractor

class MainViewModel(application: Application, private val settingIteractor: SettingIteractor) :
    AndroidViewModel(application) {

    fun setTheme() {
        settingIteractor.setTheme()
    }

    companion object {
        fun getViewModelFactory(settingIteractor: SettingIteractor): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    MainViewModel(this[APPLICATION_KEY] as Application, settingIteractor)
                }
            }
    }
}