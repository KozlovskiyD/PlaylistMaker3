package com.practicum.playlistmaker3.sharing.domain

interface SharingRepository {
    fun toShareRepository()
    fun agreement()
    fun writeTo()
}