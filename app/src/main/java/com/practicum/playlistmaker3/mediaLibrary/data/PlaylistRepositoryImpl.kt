package com.practicum.playlistmaker3.mediaLibrary.data

import com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor.PlaylistDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor.PlaylistTrackDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.data.db.AppDatabase
import com.practicum.playlistmaker3.mediaLibrary.domain.db.PlaylistRepository
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val playlistDbConvertor: PlaylistDbConvertor,
    private val playlistTrackDbConvertor: PlaylistTrackDbConvertor
) : PlaylistRepository {

    override suspend fun insertPlaylist(playlist: Playlist) {
        appDatabase.playlistDao().insertPlaylist(playlistDbConvertor.map(playlist))
    }

    private suspend fun updatePlaylist(playlist: Playlist) {
        appDatabase.playlistDao().updatePlaylist(playlistDbConvertor.map(playlist))
    }

    override suspend fun getPlaylist(): Flow<List<Playlist>> = flow {
        val playlistEntityList = appDatabase.playlistDao().getPlaylist()
        emit(playlistEntityList.map { playlistEntity -> playlistDbConvertor.map(playlistEntity) })
    }

    override suspend fun insertPlaylistTrack(playlist: Playlist, track: Track) {
        appDatabase.playlistTrackDao().insertTrack(playlistTrackDbConvertor.map(track))
        val changeablePlaylist =
            playlistDbConvertor.map(appDatabase.playlistDao().getCurrentPlaylist(playlist.id!!))
        changeablePlaylist.trackList.add(track.trackId.toLong())
        changeablePlaylist.trackCount += 1
        updatePlaylist(changeablePlaylist)
    }
}