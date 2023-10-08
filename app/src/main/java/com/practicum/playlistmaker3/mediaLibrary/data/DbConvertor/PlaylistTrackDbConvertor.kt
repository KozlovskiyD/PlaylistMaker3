package com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor

import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.PlaylistTrackEntity
import com.practicum.playlistmaker3.search.domain.models.Track

class PlaylistTrackDbConvertor {
    fun map(track: Track): PlaylistTrackEntity {
        return PlaylistTrackEntity(
            trackId = track.trackId,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl
        )
    }
}