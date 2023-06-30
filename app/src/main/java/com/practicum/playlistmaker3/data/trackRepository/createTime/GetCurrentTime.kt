package com.practicum.playlistmaker3.data.trackRepository.createTime

interface GetCurrentTime {
    fun timerDuration(): Long
    fun timerSecond(): Long
}