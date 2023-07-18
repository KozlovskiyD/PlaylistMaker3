package com.practicum.playlistmaker3.sharing.data

import com.practicum.playlistmaker3.sharing.domain.SharingRepository

class SharingRepositoryImpl(
    private val toShare: ToShare,
    private val writeToSupport: WriteToSupport,
    private val userAgreement: UserAgreement,
) : SharingRepository {

    override fun toShareRepository() {
        toShare.shareTheApp()
    }

    override fun writeTo() {
        writeToSupport.writeToSupportPlaylistMaker()
    }

    override fun agreement() {
        userAgreement.openUserAgreement()
    }
}