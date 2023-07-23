package com.practicum.playlistmaker3.player.data.mediaPlayer

import android.content.SharedPreferences
import android.media.MediaPlayer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker3.player.data.SharedPrefs.KEY_PREVIEW
import com.practicum.playlistmaker3.search.data.dto.TrackDto

const val STATE_PREPARED = 1
const val STATE_PLAYING = 2
const val STATE_PAUSED = 3
const val STATE_RELEASE = 4

class MediaPlayers(
    private val sharedPrefs: SharedPreferences,
    private var json: Gson,
    private val mediaPlayer: MediaPlayer,
) : GetMediaPlayer {

    private lateinit var currentTrack: TrackDto

    override fun sendTrack() {
        val loadGson = sharedPrefs.getString(KEY_PREVIEW, "") ?: " "
        if (loadGson != "") {
            currentTrack = json.fromJson(loadGson, object : TypeToken<TrackDto>() {}.type)
            mediaPlayer.setDataSource(currentTrack.previewUrl)
            mediaPlayer.prepareAsync()
        }
    }

    override fun controlPlayState(playerState: Int) {
        when (playerState) {
            STATE_PLAYING -> mediaPlayer.pause()
            STATE_PREPARED, STATE_PAUSED -> mediaPlayer.start()
            STATE_RELEASE -> mediaPlayer.release()
        }
    }

    override fun timerDuration(): Long {
        return mediaPlayer.duration.toLong()
    }

    override fun timerSecond(): Long {
        return mediaPlayer.currentPosition.toLong()
    }
}