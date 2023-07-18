package com.practicum.playlistmaker3.player.domain.setPreparePlayer

import com.practicum.playlistmaker3.search.domain.models.Track

interface MediaRepository {
    fun getCurrentTime(durationOrCurrent: Boolean): Long
    fun sendTrack(track: Track)
    fun controlPlayState(playerState: Int)
}
