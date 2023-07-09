package com.practicum.playlistmaker3.settings.ui.viewModelSettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker3.settings.domain.SaveThemeNightUC
import com.practicum.playlistmaker3.sharing.domain.ToShareUC
import com.practicum.playlistmaker3.sharing.domain.UserAgreementUC
import com.practicum.playlistmaker3.sharing.domain.WriteToSupportUC


class SettingsViewModel(
    private val writeToSupportUC: WriteToSupportUC,
    private val userAgreementUC: UserAgreementUC,
    private val toShareUC: ToShareUC,
    private val saveThemeNightUC: SaveThemeNightUC,
) : ViewModel() {

    private var switchCheckedLiveDataMutable = MutableLiveData<Boolean>()
    var switchCheckedLiveData: LiveData<Boolean> = switchCheckedLiveDataMutable

    fun saveSwitch(checked: Boolean) {
        saveThemeNightUC.saveTheme(checked)
    }

    fun switchCheck() {
        val loadCheck = saveThemeNightUC.loadTheme()
        switchCheckedLiveDataMutable.value = loadCheck
    }

    fun write() {
        writeToSupportUC.writeTo()
    }

    fun agreement() {
        userAgreementUC.userAgreement()
    }

    fun share() {
        toShareUC.toShare()
    }
}