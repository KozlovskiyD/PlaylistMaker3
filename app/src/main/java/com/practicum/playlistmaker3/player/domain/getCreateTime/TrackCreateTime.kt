package com.practicum.playlistmaker3.player.domain.getCreateTime

interface TrackCreateTime {
    fun getCurrentTime(durationOrCurrent: Boolean): Long
}