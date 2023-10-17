package com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.states

import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist

sealed class ListPlaylistState{
    object Empty : ListPlaylistState()
    data class Content(val playlist: List<Playlist>): ListPlaylistState()
}