package com.practicum.playlistmaker3.search.domain.api

import com.practicum.playlistmaker3.search.domain.models.Track

interface TrackRepository {
    fun searchTrack(expression: String): List<Track>
}