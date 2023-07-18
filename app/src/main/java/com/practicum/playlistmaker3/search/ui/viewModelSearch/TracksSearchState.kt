package com.practicum.playlistmaker3.search.ui.viewModelSearch

import com.practicum.playlistmaker3.search.domain.models.Track

sealed class TracksSearchState {
    object Loading : TracksSearchState()
    object Error : TracksSearchState()
    object Empty : TracksSearchState()
    data class Content(val currentTracks: List<Track>) : TracksSearchState()
    data class History(val historyTracks: List<Track>) : TracksSearchState()
}