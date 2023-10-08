package com.practicum.playlistmaker3.mediaLibrary.domain.impl

import com.practicum.playlistmaker3.mediaLibrary.domain.db.PlaylistInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.db.PlaylistRepository
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(private val playlistRepository: PlaylistRepository) :
    PlaylistInteractor {

    override suspend fun insertPlaylist(playlist: Playlist) {
        playlistRepository.insertPlaylist(playlist)
    }

    override suspend fun getPlaylist(): Flow<List<Playlist>> {
        return playlistRepository.getPlaylist()
    }

    override suspend fun insertPlaylistTrack(playlist: Playlist, track: Track) {
        playlistRepository.insertPlaylistTrack(playlist, track)
    }
}