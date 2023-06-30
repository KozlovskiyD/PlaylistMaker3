package com.practicum.playlistmaker3.data.trackRepository.mediaPlayers

import com.practicum.playlistmaker3.data.dto.TrackDto

interface TrackDtoRepository {
    fun sendTrackDto(trackDto: TrackDto): Boolean
}