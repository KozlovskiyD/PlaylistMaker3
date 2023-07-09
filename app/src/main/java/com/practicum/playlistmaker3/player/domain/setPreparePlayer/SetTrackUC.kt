package com.practicum.playlistmaker3.player.domain.setPreparePlayer

import com.practicum.playlistmaker3.search.domain.models.Track

class SetTrackUC(private val trackRepository: TrackRepository) {

    fun sendTrackInData(track: Track) {
        return trackRepository.sendTrack(track)
    }

    fun playbackControl(playerState: Int) {
        trackRepository.controlPlayState(playerState)
    }
}