package com.practicum.playlistmaker3.mediaLibrary.data

import com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor.PlaylistDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor.PlaylistTrackDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.data.db.AppDatabase
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.PlaylistTrackEntity
import com.practicum.playlistmaker3.mediaLibrary.domain.api.PlaylistRepository
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val playlistDbConvertor: PlaylistDbConvertor,
    private val playlistTrackDbConvertor: PlaylistTrackDbConvertor,
) : PlaylistRepository {

    override suspend fun insertPlaylist(playlist: Playlist) {
        appDatabase.playlistDao().insertPlaylist(playlistDbConvertor.map(playlist))
    }

    override suspend fun getPlaylist(): Flow<List<Playlist>> = flow {
        val playlistEntityList = appDatabase.playlistDao().getPlaylist()
        emit(playlistEntityList.map { playlistEntity -> playlistDbConvertor.map(playlistEntity) })
    }

    override suspend fun insertPlaylistTrack(playlist: Playlist, track: Track) {
        appDatabase.playlistTrackDao().insertTrack(playlistTrackDbConvertor.map(track))
        addTrackPlaylist(playlist.id!!, track.trackId)
    }

    override suspend fun getListTrack(trackList: List<Long>): Flow<List<Track>> = flow {
        emit(newListTrack(trackList as MutableList<Long>))
    }

    override suspend fun deleteTrackPlaylist(track: Track, playlistId: Int): List<Long> {
        val changeablePlaylist =
            playlistDbConvertor.map(appDatabase.playlistDao().getCurrentPlaylist(playlistId))
        changeablePlaylist.trackList.remove(track.trackId.toLong())
        changeablePlaylist.trackCount -= 1
        updatePlaylist(changeablePlaylist)
        updatePlaylistTrackTable(track)
        return changeablePlaylist.trackList
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        appDatabase.playlistDao().deletePlaylist(playlistDbConvertor.map(playlist))
    }

    override suspend fun editPlaylist(playlist: Playlist) {
        updatePlaylist(playlist)
    }

    override suspend fun getListTrackShare(playlist: Playlist) = flow {
        val changeablePlaylist =
            playlistDbConvertor.map(appDatabase.playlistDao().getCurrentPlaylist(playlist.id!!))
        emit(newListTrack(changeablePlaylist.trackList))
    }

    private suspend fun updatePlaylist(playlist: Playlist) {
        appDatabase.playlistDao().updatePlaylist(playlistDbConvertor.map(playlist))
    }

     override suspend fun deleteTracksPlaylist(playlist: Playlist){
        val trackListId = playlist.trackList
        val changeablePlaylist =
            playlistDbConvertor.map(appDatabase.playlistDao().getCurrentPlaylist(playlist.id!!))
        changeablePlaylist.trackList.clear()
        changeablePlaylist.trackCount = 0
        updatePlaylist(changeablePlaylist)
        trackListId.forEach { trackId ->
            val trackEntity = appDatabase.playlistTrackDao().getCurrentListTrack(trackId.toString())
            val track = playlistTrackDbConvertor.map(trackEntity)
            updatePlaylistTrackTable(track)
        }
    }

    private suspend fun newListTrack(trackList: List<Long>): List<Track> {
        val trackListString = trackList.map { trackId -> trackId.toString() }
        val newTrackEntityList = mutableListOf<PlaylistTrackEntity>()
        trackListString.indices.forEach { trackId ->
            newTrackEntityList.run {
                add(appDatabase.playlistTrackDao().getCurrentListTrack(trackListString[trackId]))
            }
        }
        return newTrackEntityList.map { playlistTrackEntity ->
            playlistTrackDbConvertor.map(
                playlistTrackEntity
            )
        }
    }

    private suspend fun addTrackPlaylist(playlistId: Int, trackId: String) {
        val changeablePlaylist =
            playlistDbConvertor.map(appDatabase.playlistDao().getCurrentPlaylist(playlistId))
        changeablePlaylist.trackList.add(0, trackId.toLong())
        changeablePlaylist.trackCount += 1
        updatePlaylist(changeablePlaylist)
    }

    private suspend fun updatePlaylistTrackTable(track: Track) {

        var isDeleteTrack = true
        val playlistEntityList = appDatabase.playlistDao().getPlaylist()
        val playlistList =
            playlistEntityList.map { playlistEntity -> playlistDbConvertor.map(playlistEntity) }
        for (item in playlistList) {
            if (item.trackList.contains(track.trackId.toLong())) {
                isDeleteTrack = false
            }
        }
        if (isDeleteTrack) appDatabase.playlistTrackDao().deleteTrack(playlistTrackDbConvertor.map(track))
    }
}