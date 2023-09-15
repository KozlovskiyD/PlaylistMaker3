package com.practicum.playlistmaker3.search.domain.impl.api

import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.util.Resource
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
     fun searchTrack(expression: String): Flow<Resource<List<Track>>>
    fun savePref(saveTrack: Track)
    fun loadPref(): List<Track>
    fun clearPref()
}