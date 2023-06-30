package com.practicum.playlistmaker3.data.trackRepository.mediaPlayer

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker3.data.dto.TrackDto
import com.practicum.playlistmaker3.data.trackRepository.createTime.GetCurrentTime

const val STATE_DEFAULT = 0
const val STATE_PREPARED = 1
const val STATE_PLAYING = 2
const val STATE_PAUSED = 3
const val STATE_RELEASE = 4


class MediaPlayers(context: Context) : GetCurrentTime {

    private val mediaPlayers = MediaPlayer()
    private lateinit var currentTrack: TrackDto
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREVIEW_URL, Context.MODE_PRIVATE)

    fun sendTrackDto() {
        val loadGson = sharedPrefs.getString(KEY_PREVIEW, "") ?: " "
        if (loadGson != "") {
            currentTrack = Gson().fromJson(loadGson, object : TypeToken<TrackDto>() {}.type)
            mediaPlayers.setDataSource(currentTrack.previewUrl)
            mediaPlayers.prepareAsync()
        }
    }

    fun playbackControls(playerState: Int) {
        when (playerState) {
            STATE_PLAYING -> mediaPlayers.pause()
            STATE_PREPARED, STATE_PAUSED -> mediaPlayers.start()
            STATE_RELEASE -> mediaPlayers.release()
        }
    }

    override fun timerDuration(): Long {
        return mediaPlayers.duration.toLong()
    }

    override fun timerSecond(): Long {
        return mediaPlayers.currentPosition.toLong()
    }
}