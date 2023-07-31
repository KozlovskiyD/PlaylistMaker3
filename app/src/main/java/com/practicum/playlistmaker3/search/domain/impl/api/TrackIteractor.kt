package com.practicum.playlistmaker3.search.domain.impl.api

import com.practicum.playlistmaker3.search.domain.models.Track

interface TrackIteractor {
    fun searchTrack(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(foundTrack: List<Track>?, additionalMessage: String?)
    }

    fun savePref(saveTrack: Track)
    fun loadPref(): List<Track>
    fun clearPref()
}