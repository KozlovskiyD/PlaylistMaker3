package com.practicum.playlistmaker3.player.domain.setPreparePlayer

import com.practicum.playlistmaker3.search.domain.models.Track

interface TrackRepository {
    fun sendTrack(track: Track)
    fun controlPlayState(playerState: Int)
}
