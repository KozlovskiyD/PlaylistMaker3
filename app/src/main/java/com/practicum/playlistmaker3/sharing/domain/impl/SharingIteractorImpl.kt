package com.practicum.playlistmaker3.sharing.domain.impl

import com.practicum.playlistmaker3.sharing.domain.api.SharingIteractor
import com.practicum.playlistmaker3.sharing.domain.api.SharingRepository

class SharingIteractorImpl(private val sharingRepository: SharingRepository) : SharingIteractor {

    override fun toShareRepository() {
        sharingRepository.toShareRepository()
    }

    override fun agreement() {
        sharingRepository.agreement()
    }

    override fun writeTo() {
        sharingRepository.writeTo()
    }
}