package com.practicum.playlistmaker3.domain.getCreateTime

interface TrackCreateTime {
    fun getCurrentTime(durationOrCurrent: Boolean): Long
}