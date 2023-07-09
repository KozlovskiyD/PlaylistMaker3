package com.practicum.playlistmaker3.sharing.data

import android.content.Context
import com.practicum.playlistmaker3.sharing.domain.SharingRepository

class SharingRepositoryImpl(
    private val applicationContext: Context,
    private val toShare: ToShare,
    private val writeToSupport: WriteToSupport,
    private val userAgreement: UserAgreement,
) : SharingRepository {

    override fun toShareRepository() {
        toShare.shareTheApp(applicationContext)
    }

    override fun writeTo() {
        writeToSupport.writeToSupportPlaylistMaker(applicationContext)
    }

    override fun agreement() {
        userAgreement.openUserAgreement(applicationContext)
    }
}