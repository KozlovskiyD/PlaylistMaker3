package com.practicum.playlistmaker3.data.trackRepository.createTime

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.practicum.playlistmaker3.domain.getCreateTime.TrackCreateTime

class CreateTimeImpl : TrackCreateTime {

    private val mediaPlayer = MediaPlayer()
    var mainThreadHandler: Handler? = null
    var currentSecond = 0L

    override fun getCurrentTime(): Long {
        mainThreadHandler = Handler(Looper.getMainLooper())
        mainThreadHandler?.post(createTime())
        return currentSecond
    }

    private fun createTime(): Runnable {
        return object : Runnable {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun run() {
                val duration = mediaPlayer.currentPosition.toLong()
                var second = mediaPlayer.duration.toLong()
                if (second > 0) {
                    second = duration / 1000L
                    currentSecond = second
                    mainThreadHandler?.postDelayed(this, DELAY_DEFAULT)
                }
            }
        }
    }

    companion object {
        const val DELAY_DEFAULT = 500L
    }
}