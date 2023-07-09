package com.practicum.playlistmaker3.search.domain.impl

import com.practicum.playlistmaker3.search.domain.api.TrackIteractor
import com.practicum.playlistmaker3.search.domain.api.TrackRepository
import java.util.concurrent.Executors

class TrackIteractorImpl(private val repository: TrackRepository): TrackIteractor {
    private val executor = Executors.newCachedThreadPool()

    override fun searchTrack(expression: String, consumer: TrackIteractor.TrackConsumer) {
        val thread = Thread {
        consumer.consume(repository.searchTrack(expression))
        }
        thread.start()
    }
}