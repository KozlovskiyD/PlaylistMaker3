package com.practicum.playlistmaker3.sharing.domain

class UserAgreementUC(private val sharingRepository: SharingRepository) {
    fun userAgreement() {
        sharingRepository.agreement()
    }
}