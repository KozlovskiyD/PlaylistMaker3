package com.practicum.playlistmaker3.data.trackRepository.mediaPlayers

import android.media.MediaPlayer
import android.os.Handler
import com.practicum.playlistmaker3.data.dto.TrackDto

const val STATE_DEFAULT = 0
const val STATE_PREPARED = 1
const val STATE_PLAYING = 2
const val STATE_PAUSED = 3
const val STATE_RELEASE = 4
const val STATE_HANDLER = 5

class MediaPlayers : TrackDtoRepository {

    private val mediaPlayer = MediaPlayer()
    private val mainThreadHandler: Handler? = null
    private var buttonPlayAndPauseToPlay = false
    override fun sendTrackDto(trackDto: TrackDto): Boolean {

        mediaPlayer.setDataSource(trackDto.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            buttonPlayAndPauseToPlay = true
        }
        mediaPlayer.setOnCompletionListener {
            mainThreadHandler?.removeCallbacksAndMessages(null)
            buttonPlayAndPauseToPlay = true
        }
        return buttonPlayAndPauseToPlay
    }

    fun playbackControls(playerState: Int) {
        when (playerState) {
            STATE_PLAYING -> mediaPlayer.pause()
            STATE_PREPARED, STATE_PAUSED -> mediaPlayer.start()
            STATE_RELEASE -> mediaPlayer.release()
            STATE_HANDLER -> mainThreadHandler?.removeCallbacksAndMessages(null)
        }
    }

}