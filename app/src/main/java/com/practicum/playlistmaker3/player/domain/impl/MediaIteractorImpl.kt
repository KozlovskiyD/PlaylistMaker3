package com.practicum.playlistmaker3.player.domain.impl

import com.practicum.playlistmaker3.player.domain.api.MediaIteractor
import com.practicum.playlistmaker3.player.domain.api.MediaRepository
import com.practicum.playlistmaker3.search.domain.models.Track

class MediaIteractorImpl(private val mediaRepository: MediaRepository) : MediaIteractor {

    override fun getCurrentTime(durationOrCurrent: Boolean): Long {
        return mediaRepository.getCurrentTime(durationOrCurrent)
    }

    override fun sendTrack(track: Track) {
        mediaRepository.sendTrack(track)
    }

    override fun controlPlayState(playerState: Int) {
        mediaRepository.controlPlayState(playerState)
    }
}