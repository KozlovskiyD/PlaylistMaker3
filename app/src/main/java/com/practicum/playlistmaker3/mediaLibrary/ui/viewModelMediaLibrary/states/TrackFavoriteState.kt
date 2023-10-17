package com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.states

import com.practicum.playlistmaker3.search.domain.models.Track

sealed class TrackFavoriteState {
    object Empty : TrackFavoriteState()
    data class Content(val FavoriteTracks: List<Track>) : TrackFavoriteState()
}
