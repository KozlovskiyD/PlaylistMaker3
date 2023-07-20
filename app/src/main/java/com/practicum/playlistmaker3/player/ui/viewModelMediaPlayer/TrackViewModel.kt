package com.practicum.playlistmaker3.player.ui.viewModelMediaPlayer

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker3.player.domain.api.MediaIteractor
import com.practicum.playlistmaker3.player.domain.screenModel.ScreenMediaModel
import com.practicum.playlistmaker3.player.ui.viewActivity.*
import com.practicum.playlistmaker3.search.domain.models.Track

@SuppressLint("StaticFieldLeak")
class TrackViewModel(application: Application, private val mediaIteractor: MediaIteractor) :
    AndroidViewModel(application) {

    private var mediaLiveDataMutable = MutableLiveData<ScreenMediaModel>()
    var mediaLiveData: LiveData<ScreenMediaModel> = mediaLiveDataMutable

    private var playerState = STATE_DEFAULT
    var mainThreadHandler: Handler? = null
    var duration = 0L
    var currentSecond = 0L
    var screen = ScreenMediaModel(false, 0L)

    fun preparePlayer(currentTrackPreviewUrl: Track) {
        playerState = STATE_PREPARED
        mediaIteractor.sendTrack(currentTrackPreviewUrl)
    }

    private fun createTime(): Runnable {
        duration = (mediaIteractor.getCurrentTime(true)) / THOUSAND_L
        return object : Runnable {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun run() {
                currentSecond = (mediaIteractor.getCurrentTime(false)) / THOUSAND_L
                screen.time = currentSecond
                mediaLiveDataMutable.value = screen
                mainThreadHandler?.postDelayed(this, DELAY_DEFAULT)
                if (duration == currentSecond) {
                    stopTimer()
                    screen.playButton = true
                    mediaLiveDataMutable.value = screen
                    screen.time = 0L
                    mediaLiveDataMutable.value = screen
                    playerState = STATE_PREPARED
                }
            }
        }
    }

    fun playbackControl() {
        mediaIteractor.controlPlayState(playerState)
        when (playerState) {
            STATE_PLAYING -> {
                screen.playButton = true
                mediaLiveDataMutable.value = screen
                playerState = STATE_PAUSED
            }
            STATE_PREPARED, STATE_PAUSED -> {
                screen.playButton = false
                mediaLiveDataMutable.value = screen
                playerState = STATE_PLAYING
            }
        }
    }

    fun playerStateChange(state: Int) {
        playerState = state
        playbackControl()
    }

    fun stopTimer() {
        mainThreadHandler?.removeCallbacksAndMessages(null)
    }

    fun startCreateTime() {
        mainThreadHandler = Handler(Looper.getMainLooper())
        mainThreadHandler?.post(createTime())
    }

    companion object {
        fun getViewModelFactory(mediaIteractor: MediaIteractor): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    TrackViewModel(this[APPLICATION_KEY] as Application, mediaIteractor)
                }
            }
    }
}