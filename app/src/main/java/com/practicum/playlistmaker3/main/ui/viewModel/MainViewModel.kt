package com.practicum.playlistmaker3.main.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker3.util.Creator

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val settingRepository = Creator.settingRepository(getApplication())

    fun setTheme() {
        settingRepository.setTheme()
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }
}