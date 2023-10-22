package com.practicum.playlistmaker3.mediaLibrary.domain.api

import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun insertPlaylist(playlist: Playlist)
    suspend fun getPlaylist(): Flow<List<Playlist>>
    suspend fun insertPlaylistTrack(playlist: Playlist, track: Track)
    suspend fun getListTrack(trackList: List<Long>): Flow<List<Track>>
    suspend fun deleteTrackPlaylist(track: Track, playlistId: Int): List<Long>
    suspend fun deletePlaylist(playlist: Playlist)
    suspend fun editPlaylist(playlist: Playlist)
    suspend fun deleteTracksPlaylist(playlist: Playlist)
}