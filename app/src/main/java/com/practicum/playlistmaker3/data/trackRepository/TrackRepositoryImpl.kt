package com.practicum.playlistmaker3.data.trackRepository

import com.practicum.playlistmaker3.data.dto.TrackDto
import com.practicum.playlistmaker3.data.trackRepository.mediaPlayers.MediaPlayers
import com.practicum.playlistmaker3.data.trackRepository.mediaPlayers.TrackDtoRepository
import com.practicum.playlistmaker3.domain.models.Track
import com.practicum.playlistmaker3.domain.setPreparePlayer.TrackRepository

class TrackRepositoryImpl(private val trackDtoRepository: TrackDtoRepository): TrackRepository {
    private val preparePlayer = MediaPlayers()
    override  fun sendTrack(track: Track): Boolean {
        return trackDtoRepository.sendTrackDto(mapToTrackDto(track))
    }

    override fun controlPlayState(playerState: Int) {
        preparePlayer.playbackControls(playerState)
    }

    private fun mapToTrackDto(track: Track): TrackDto {
        return TrackDto(
            trackId = track.trackId,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl)
    }
}
