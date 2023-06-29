package com.practicum.playlistmaker3.data.trackRepository

import android.content.Context
import com.practicum.playlistmaker3.data.dto.TrackDto
import com.practicum.playlistmaker3.data.trackRepository.mediaPlayers.MediaPlayers
import com.practicum.playlistmaker3.data.trackRepository.mediaPlayers.SharedPrefs
import com.practicum.playlistmaker3.domain.models.Track
import com.practicum.playlistmaker3.domain.setPreparePlayer.TrackRepository

class TrackRepositoryImpl(applicationContext: Context, private val mediaPlayers: MediaPlayers) :
    TrackRepository {
    private val sharedPrefs = SharedPrefs(applicationContext)

    override fun sendTrack(track: Track) {
        sharedPrefs.sharedPref(mapToTrackDto(track))
        return mediaPlayers.sendTrackDto()
    }

    override fun controlPlayState(playerState: Int) {
        mediaPlayers.playbackControls(playerState)
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
            previewUrl = track.previewUrl
        )
    }
}
