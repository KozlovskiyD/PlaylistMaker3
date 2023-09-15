package com.practicum.playlistmaker3.search.domain.impl.api

import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackIteractor {
     fun searchTrack(expression: String): Flow<Pair<List<Track>?, String?>>
    fun savePref(saveTrack: Track)
    fun loadPref(): List<Track>
    fun clearPref()
}