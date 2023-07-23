package com.practicum.playlistmaker3.search.domain.impl


import com.practicum.playlistmaker3.search.domain.impl.api.TrackIteractor
import com.practicum.playlistmaker3.search.domain.impl.api.TrackRepository
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.util.Resource
import java.util.concurrent.Executors

class TrackIteractorImpl(private val repository: TrackRepository) : TrackIteractor {
    private val executor = Executors.newCachedThreadPool()

    override fun searchTrack(expression: String, consumer: TrackIteractor.TrackConsumer) {

        executor.execute {
            when (val resource = repository.searchTrack(expression)) {
                is Resource.Success -> consumer.consume(resource.data, "")
                is Resource.Error -> consumer.consume(null, resource.message)
            }
        }
    }

    override fun savePref(saveTrack: Track) {
        repository.savePref(saveTrack)
    }

    override fun loadPref(): List<Track> {
        return repository.loadPref()
    }

    override fun clearPref() {
        repository.clearPref()
    }
}