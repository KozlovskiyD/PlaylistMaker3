package com.practicum.playlistmaker3.player.data

import android.content.Context
import com.practicum.playlistmaker3.player.data.SharedPrefs.SharedPrefs
import com.practicum.playlistmaker3.player.data.mediaPlayer.GetMediaPlayer
import com.practicum.playlistmaker3.player.domain.api.MediaRepository
import com.practicum.playlistmaker3.search.data.dto.TrackDto
import com.practicum.playlistmaker3.search.domain.models.Track

class MediaRepositoryImpl(context: Context, private val getCurrentTime: GetMediaPlayer) :
    MediaRepository {
    private val sharedPrefs = SharedPrefs(context)

    override fun getCurrentTime(durationOrCurrent: Boolean): Long {
        return if (durationOrCurrent) getCurrentTime.timerDuration() else getCurrentTime.timerSecond()
    }

    override fun sendTrack(track: Track) {
        sharedPrefs.sharedPref(mapToTrackDto(track))
        return getCurrentTime.sendTrack()
    }

    override fun controlPlayState(playerState: Int) {
        getCurrentTime.controlPlayState(playerState)
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
