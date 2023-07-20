package com.practicum.playlistmaker3.sharing.domain.api

interface SharingRepository {
    fun toShareRepository()
    fun agreement()
    fun writeTo()
}