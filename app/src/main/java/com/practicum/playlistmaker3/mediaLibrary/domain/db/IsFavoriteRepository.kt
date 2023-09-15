package com.practicum.playlistmaker3.mediaLibrary.domain.db

import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.TrackId
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface IsFavoriteRepository {
    suspend fun insertTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    suspend fun isFavoriteTracks(): Flow<List<Track>>
    suspend fun isFavoriteTrackListId(): Flow<List<TrackId>>
}