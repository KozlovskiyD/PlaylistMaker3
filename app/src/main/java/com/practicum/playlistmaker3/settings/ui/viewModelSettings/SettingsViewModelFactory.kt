package com.practicum.playlistmaker3.settings.ui.viewModelSettings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker3.settings.data.SaveThemeNightImpl
import com.practicum.playlistmaker3.settings.domain.SaveThemeNightUC
import com.practicum.playlistmaker3.sharing.data.SharingRepositoryImpl
import com.practicum.playlistmaker3.sharing.data.ToShare
import com.practicum.playlistmaker3.sharing.data.UserAgreement
import com.practicum.playlistmaker3.sharing.data.WriteToSupport
import com.practicum.playlistmaker3.sharing.domain.ToShareUC
import com.practicum.playlistmaker3.sharing.domain.UserAgreementUC
import com.practicum.playlistmaker3.sharing.domain.WriteToSupportUC

@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val toShare by lazy { ToShare() }
    private val userAgreement by lazy { UserAgreement() }
    private val writeToSupport by lazy { WriteToSupport() }
    private val sharingRepositoryImpl by lazy {
        SharingRepositoryImpl(
            context,
            toShare,
            writeToSupport,
            userAgreement,
        )
    }
    private val saveThemeNightImpl by lazy { SaveThemeNightImpl(context) }
    private val writeToSupportUC by lazy { WriteToSupportUC(sharingRepositoryImpl) }
    private val userAgreementUC by lazy { UserAgreementUC(sharingRepositoryImpl) }
    private val toShareUC by lazy { ToShareUC(sharingRepositoryImpl) }
    private val saveThemeNightUC by lazy { SaveThemeNightUC(saveThemeNightImpl) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(writeToSupportUC,
            userAgreementUC,
            toShareUC,
            saveThemeNightUC) as T
    }
}