package com.practicum.playlistmaker3.mediaLibrary.domain.db

import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun insertPlaylist(playlist: Playlist)
    suspend fun getPlaylist(): Flow<List<Playlist>>
    suspend fun insertPlaylistTrack(playlist: Playlist, track: Track)
}