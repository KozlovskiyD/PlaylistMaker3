package com.practicum.playlistmaker3.player.ui.viewModelMediaPlayer

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker3.player.domain.api.MediaIteractor
import com.practicum.playlistmaker3.player.domain.screenModel.ScreenMediaModel
import com.practicum.playlistmaker3.player.ui.viewActivity.STATE_DEFAULT
import com.practicum.playlistmaker3.player.ui.viewActivity.STATE_PAUSED
import com.practicum.playlistmaker3.player.ui.viewActivity.STATE_PLAYING
import com.practicum.playlistmaker3.player.ui.viewActivity.STATE_PREPARED
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class TrackViewModel(private val mediaIteractor: MediaIteractor) : ViewModel() {

    private var mediaLiveDataMutable = MutableLiveData<ScreenMediaModel>()
    var mediaLiveData: LiveData<ScreenMediaModel> = mediaLiveDataMutable

    private var playerState = STATE_DEFAULT
    private var timerJob: Job? = null
    var duration = 0L
    var currentSecond = 0L
    var screen = ScreenMediaModel(false, 0L)

    fun preparePlayer(currentTrackPreviewUrl: Track) {
        playerState = STATE_PREPARED
        mediaIteractor.sendTrack(currentTrackPreviewUrl)
    }

     fun startCreateTime(){
        duration = (mediaIteractor.getCurrentTime(true))
        timerJob = viewModelScope.launch {
            while (playerState == STATE_PLAYING) {
                delay(300L)
                currentSecond = (mediaIteractor.getCurrentTime(false))
                screen.time = currentSecond
                mediaLiveDataMutable.value = screen
                if (currentSecond >= duration) {
                    screen.playButton = true
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
            STATE_PLAYING -> playerPause()
            STATE_PREPARED, STATE_PAUSED -> playerStart()
        }
    }

    private fun playerStart(){
        screen.playButton = false
        mediaLiveDataMutable.value = screen
        playerState = STATE_PLAYING
    }

    private fun playerPause(){
        timerJob?.cancel()
        screen.playButton = true
        mediaLiveDataMutable.value = screen
        playerState = STATE_PAUSED
    }

    fun playerStateChange(state: Int) {
        playerState = state
        playbackControl()
    }
}