package com.practicum.playlistmaker3.sharing.domain

class ToShareUC(private val sharingRepository: SharingRepository) {
    fun toShare() {
        sharingRepository.toShareRepository()
    }
}