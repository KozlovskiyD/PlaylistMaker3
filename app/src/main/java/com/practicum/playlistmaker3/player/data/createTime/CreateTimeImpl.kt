package com.practicum.playlistmaker3.player.data.createTime


import com.practicum.playlistmaker3.player.data.mediaPlayer.GetCurrentTime
import com.practicum.playlistmaker3.player.domain.getCreateTime.TrackCreateTime

class CreateTimeImpl(private val getCurrentTime: GetCurrentTime) : TrackCreateTime {

    override fun getCurrentTime(durationOrCurrent: Boolean): Long {
        return if (durationOrCurrent) getCurrentTime.timerDuration() else getCurrentTime.timerSecond()
    }
}