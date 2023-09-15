package com.practicum.playlistmaker3.mediaLibrary.domain.impl

import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.TrackId
import com.practicum.playlistmaker3.mediaLibrary.domain.db.IsFavoriteInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.db.IsFavoriteRepository
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class IsFavoriteInteractorImpl(private val isFavoriteRepository: IsFavoriteRepository): IsFavoriteInteractor {

    override suspend fun insertTrack(track: Track) {
        isFavoriteRepository.insertTrack(track)
    }

    override suspend fun deleteTrack(track: Track) {
        isFavoriteRepository.deleteTrack(track)
    }

    override suspend fun isFavoriteTracks(): Flow<List<Track>> {
        return isFavoriteRepository.isFavoriteTracks()
    }

    override suspend fun isFavoriteTrackListId(): Flow<List<TrackId>> {
        return isFavoriteRepository.isFavoriteTrackListId()
    }
}