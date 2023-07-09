package com.practicum.playlistmaker3.player.data.mediaPlayer

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker3.search.data.dto.TrackDto
import com.practicum.playlistmaker3.player.data.SharedPrefs.KEY_PREVIEW
import com.practicum.playlistmaker3.player.data.SharedPrefs.PREVIEW_URL

const val STATE_DEFAULT = 0
const val STATE_PREPARED = 1
const val STATE_PLAYING = 2
const val STATE_PAUSED = 3
const val STATE_RELEASE = 4


class MediaPlayers(context: Context) : GetCurrentTime {

    private val mediaPlayer = MediaPlayer()
    private lateinit var currentTrack: TrackDto
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREVIEW_URL, Context.MODE_PRIVATE)

    fun sendTrackDto() {
        val loadGson = sharedPrefs.getString(KEY_PREVIEW, "") ?: " "
        if (loadGson != "") {
            currentTrack = Gson().fromJson(loadGson, object : TypeToken<TrackDto>() {}.type)
            mediaPlayer.setDataSource(currentTrack.previewUrl)
            mediaPlayer.prepareAsync()
        }
    }

    fun playbackControls(playerState: Int) {
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