package com.practicum.playlistmaker3.domain.setPreparePlayer

import com.practicum.playlistmaker3.domain.models.Track

class SetTrackUC(private val trackRepository: TrackRepository) {

     fun sendTrackInData(track: Track): Boolean {
        return trackRepository.sendTrack(track)
    }

    fun playbackControl(playerState: Int) {
        trackRepository.controlPlayState(playerState)
    }
}