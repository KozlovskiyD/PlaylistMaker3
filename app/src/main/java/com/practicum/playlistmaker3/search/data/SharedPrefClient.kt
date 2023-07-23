package com.practicum.playlistmaker3.search.data

import com.practicum.playlistmaker3.search.data.dto.TrackDto

interface SharedPrefClient {
    fun savePref(saveTrack: TrackDto)
    fun loadPref(): List<TrackDto>
    fun clearPref()
}