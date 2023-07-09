package com.practicum.playlistmaker3.search.domain.api

import com.practicum.playlistmaker3.search.domain.models.Track

interface TrackIteractor {
    fun searchTrack(expression: String, consumer: TrackConsumer)

    interface TrackConsumer{
        fun consume(foundTrack: List<Track>)
    }
}