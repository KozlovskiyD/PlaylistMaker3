package com.practicum.playlistmaker3.player.ui.viewModelMediaPlayer

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.player.data.mediaPlayer.STATE_DEFAULT
import com.practicum.playlistmaker3.player.data.mediaPlayer.STATE_PAUSED
import com.practicum.playlistmaker3.player.data.mediaPlayer.STATE_PLAYING
import com.practicum.playlistmaker3.player.data.mediaPlayer.STATE_PREPARED
import com.practicum.playlistmaker3.player.domain.getCreateTime.GetCreateTimeUC
import com.practicum.playlistmaker3.player.domain.setPreparePlayer.SetTrackUC
import com.practicum.playlistmaker3.player.ui.viewActivity.DELAY_DEFAULT
import com.practicum.playlistmaker3.player.ui.viewActivity.THOUSAND_L

@SuppressLint("StaticFieldLeak")
class TrackViewModel(
    private val setTrackUC: SetTrackUC,
    private val getCreateTimeUC: GetCreateTimeUC,
) : ViewModel() {

    private var playerLiveDataMutable = MutableLiveData<Boolean>()
    private var timerLiveDataMutable = MutableLiveData<Long>()
    private var playerState = STATE_DEFAULT
    var mainThreadHandler: Handler? = null
    var duration = 0L
    var currentsecond = 0L
    var playerLiveData: LiveData<Boolean> = playerLiveDataMutable
    var timerLiveData: LiveData<Long> = timerLiveDataMutable

    fun preparePlayer(currentTrackPreviewUrl: Track) {
        playerState = STATE_PREPARED
        setTrackUC.sendTrackInData(currentTrackPreviewUrl)
    }

    private fun createTime(): Runnable {
        duration = (getCreateTimeUC.getCurrentTime(true)) / THOUSAND_L
        return object : Runnable {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun run() {
                currentsecond = (getCreateTimeUC.getCurrentTime(false)) / THOUSAND_L
                timerLiveDataMutable.value = currentsecond
                mainThreadHandler?.postDelayed(this, DELAY_DEFAULT)
                if (duration == currentsecond) {
                    stopTimer()
                    playerLiveDataMutable.value = true
                    timerLiveDataMutable.value = 0L
                    playerState = STATE_PREPARED
                }
            }
        }
    }

    fun playbackControl() {
        setTrackUC.playbackControl(playerState)
        when (playerState) {
            STATE_PLAYING -> {
                playerLiveDataMutable.value = true
                playerState = STATE_PAUSED
            }
            STATE_PREPARED, STATE_PAUSED -> {
                playerLiveDataMutable.value = false
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
}