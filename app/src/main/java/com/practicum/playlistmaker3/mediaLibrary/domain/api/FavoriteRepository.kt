package com.practicum.playlistmaker3.mediaLibrary.domain.api

import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.TrackId
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    suspend fun isFavoriteTracks(): Flow<List<Track>>
    suspend fun isFavoriteTrackListId(): Flow<List<TrackId>>
}