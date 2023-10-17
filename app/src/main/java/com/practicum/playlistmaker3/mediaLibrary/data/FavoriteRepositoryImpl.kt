package com.practicum.playlistmaker3.mediaLibrary.data

import com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor.TrackDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.data.db.AppDatabase
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.TrackEntity
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.TrackId
import com.practicum.playlistmaker3.mediaLibrary.domain.api.FavoriteRepository
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor
) : FavoriteRepository {

    override suspend fun insertTrack(track: Track) {
        appDatabase.trackDao().insertTrack(convertFromTrack(track))
    }

    override suspend fun deleteTrack(track: Track) {
        appDatabase.trackDao().deleteTrack(convertFromTrack(track))
    }

    override suspend fun isFavoriteTracks(): Flow<List<Track>> = flow {
        val trackEntityList = appDatabase.trackDao().getTracks()
        val newTrackEntityList = mutableListOf<TrackEntity>()
        trackEntityList.indices.forEach { i ->
            newTrackEntityList.run { add(0, trackEntityList[(i)]) }
        }
        emit(convertFromTrackEntity(newTrackEntityList))
    }

    override suspend fun isFavoriteTrackListId(): Flow<List<TrackId>> = flow {
        val trackListId = appDatabase.trackDao().getListId()
        emit(trackListId)
    }

    private fun convertFromTrack(track: Track): TrackEntity {
        return trackDbConvertor.map(track)
    }

    private fun convertFromTrackEntity(trackEntityList: List<TrackEntity>): List<Track> {
        return trackEntityList.map { trackEntity -> trackDbConvertor.map(trackEntity) }
    }
}