package com.practicum.playlistmaker3.sharing.domain

class WriteToSupportUC(private val sharingRepository: SharingRepository) {
    fun writeTo() {
        sharingRepository.writeTo()
    }
}