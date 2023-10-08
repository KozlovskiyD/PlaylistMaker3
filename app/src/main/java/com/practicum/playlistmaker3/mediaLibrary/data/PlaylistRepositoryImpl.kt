package com.practicum.playlistmaker3.mediaLibrary.data

import com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor.PlaylistDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor.PlaylistTrackDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.data.db.AppDatabase
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.PlaylistEntity
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.PlaylistTrackEntity
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
        appDatabase.playlistDao().insertPlaylist(convertFromPlaylist(playlist))
    }

    private suspend fun updatePlaylist(playlist: Playlist) {
        appDatabase.playlistDao().updatePlaylist(convertFromPlaylist(playlist))
    }

    override suspend fun getPlaylist(): Flow<List<Playlist>> = flow {
        val playlistEntityList = appDatabase.playlistDao().getPlaylist()
        emit(convertFromListPlaylistEntity(playlistEntityList))
    }

    override suspend fun insertPlaylistTrack(playlist: Playlist, track: Track) {
        appDatabase.playlistTrackDao().insertTrack(convertFromPlaylistTrack(track))
        val changeablePlaylist =
            convertFromPlaylistEntity(appDatabase.playlistDao().getCurrentPlaylist(playlist.id!!))
        changeablePlaylist.trackList.add(track.trackId.toLong())
        changeablePlaylist.trackCount += 1
        updatePlaylist(changeablePlaylist)
    }

    private fun convertFromPlaylist(playlist: Playlist): PlaylistEntity {
        return playlistDbConvertor.map(playlist)
    }

    private fun convertFromPlaylistEntity(playlistEntity: PlaylistEntity): Playlist {
        return playlistDbConvertor.map(playlistEntity)
    }

    private fun convertFromListPlaylistEntity(playlistEntityList: List<PlaylistEntity>): List<Playlist> {
        return playlistEntityList.map { playlistEntity -> playlistDbConvertor.map(playlistEntity) }
    }

    private fun convertFromPlaylistTrack(track: Track): PlaylistTrackEntity {
        return playlistTrackDbConvertor.map(track)
    }
}