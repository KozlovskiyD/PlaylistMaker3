package com.practicum.playlistmaker3.settings.ui.viewModelSettings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker3.util.Creator


class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val sharingRepository = Creator.getSharingRepository(getApplication())
    private val settingRepository = Creator.settingRepository(getApplication())

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }

    private var switchCheckedLiveDataMutable = MutableLiveData<Boolean>()
    var switchCheckedLiveData: LiveData<Boolean> = switchCheckedLiveDataMutable

    fun saveSwitch(checked: Boolean) {
        settingRepository.themeNight(checked)
    }

    fun switchCheck() {
        val loadCheck = settingRepository.loadTheme()
        switchCheckedLiveDataMutable.value = loadCheck
    }

    fun write() {
        sharingRepository.writeTo()
    }

    fun agreement() {
        sharingRepository.agreement()
    }

    fun share() {
        sharingRepository.toShareRepository()
    }
}