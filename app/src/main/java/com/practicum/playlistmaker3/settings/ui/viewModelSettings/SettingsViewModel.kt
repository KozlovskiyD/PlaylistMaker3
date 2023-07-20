package com.practicum.playlistmaker3.settings.ui.viewModelSettings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker3.settings.domain.api.SettingIteractor
import com.practicum.playlistmaker3.sharing.domain.api.SharingIteractor


class SettingsViewModel(
    application: Application,
    private val settingIteractor: SettingIteractor,
    private val sharingIteractor: SharingIteractor,
) : AndroidViewModel(application) {

    private var switchCheckedLiveDataMutable = MutableLiveData<Boolean>()
    var switchCheckedLiveData: LiveData<Boolean> = switchCheckedLiveDataMutable

    fun saveSwitch(checked: Boolean) {
        settingIteractor.themeNight(checked)
    }

    fun switchCheck() {
        val loadCheck = settingIteractor.loadTheme()
        switchCheckedLiveDataMutable.value = loadCheck
    }

    fun write() {
        sharingIteractor.writeTo()
    }

    fun agreement() {
        sharingIteractor.agreement()
    }

    fun share() {
        sharingIteractor.toShareRepository()
    }

    companion object {
        fun getViewModelFactory(
            settingIteractor: SettingIteractor,
            sharingIteractor: SharingIteractor,
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(this[APPLICATION_KEY] as Application,
                    settingIteractor,
                    sharingIteractor)
            }
        }
    }
}