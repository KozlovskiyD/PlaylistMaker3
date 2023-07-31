package com.practicum.playlistmaker3.settings.ui.viewModelSettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker3.settings.domain.api.SettingIteractor
import com.practicum.playlistmaker3.sharing.domain.api.SharingIteractor


class SettingsViewModel(
    private val settingIteractor: SettingIteractor,
    private val sharingIteractor: SharingIteractor,
) : ViewModel() {

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
}