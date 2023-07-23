package com.practicum.playlistmaker3.player.data.mediaPlayer

interface GetMediaPlayer {
    fun timerDuration(): Long
    fun timerSecond(): Long
    fun sendTrack()
    fun controlPlayState(playerState: Int)
}