package com.practicum.playlistmaker3.mediaLibrary.domain.impl

import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.TrackId
import com.practicum.playlistmaker3.mediaLibrary.domain.api.FavoriteInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.api.FavoriteRepository
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavoriteInteractorImpl(private val isFavoriteRepository: FavoriteRepository): FavoriteInteractor {

    override suspend fun insertTrack(track: Track) {
        isFavoriteRepository.insertTrack(track)
    }

    override suspend fun deleteTrack(track: Track) {
        isFavoriteRepository.deleteTrack(track)
    }

    override suspend fun favoriteTracks(): Flow<List<Track>> {
        return isFavoriteRepository.isFavoriteTracks()
    }

    override suspend fun favoriteTrackListId(): Flow<List<TrackId>> {
        return isFavoriteRepository.isFavoriteTrackListId()
    }
}