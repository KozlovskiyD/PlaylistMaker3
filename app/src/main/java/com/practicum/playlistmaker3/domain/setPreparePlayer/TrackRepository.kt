package com.practicum.playlistmaker3.domain.setPreparePlayer

import com.practicum.playlistmaker3.domain.models.Track

interface TrackRepository {
     fun sendTrack(track: Track): Boolean
     fun controlPlayState(playerState: Int)
}
