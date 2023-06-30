package com.practicum.playlistmaker3.data.trackRepository.createTime


import com.practicum.playlistmaker3.domain.getCreateTime.TrackCreateTime

class CreateTimeImpl(private val getCurrentTime: GetCurrentTime) : TrackCreateTime {

    override fun getCurrentTime(durationOrCurrent: Boolean): Long {
        return if (durationOrCurrent) getCurrentTime.timerDuration() else getCurrentTime.timerSecond()
    }
}