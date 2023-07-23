package com.practicum.playlistmaker3.player.domain.api

import com.practicum.playlistmaker3.search.domain.models.Track

interface MediaIteractor {
    fun getCurrentTime(durationOrCurrent: Boolean): Long
    fun sendTrack(track: Track)
    fun controlPlayState(playerState: Int)
}