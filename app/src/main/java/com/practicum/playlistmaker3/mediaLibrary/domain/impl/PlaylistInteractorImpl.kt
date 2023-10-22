package com.practicum.playlistmaker3.mediaLibrary.domain.impl

import com.practicum.playlistmaker3.mediaLibrary.domain.api.PlaylistInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.api.PlaylistRepository
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

    override suspend fun getListTrack(trackList: List<Long>): Flow<List<Track>> {
       return playlistRepository.getListTrack(trackList)
    }

    override suspend fun deleteTrackPlaylist(track: Track, playlistId: Int): List<Long> {
        return playlistRepository.deleteTrackPlaylist(track, playlistId)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistRepository.deletePlaylist(playlist)
    }

    override suspend fun editPlaylist(playlist: Playlist) {
        playlistRepository.editPlaylist(playlist)
    }

    override suspend fun deleteTracksPlaylist(playlist: Playlist) {
        playlistRepository.deleteTracksPlaylist(playlist)
    }
}