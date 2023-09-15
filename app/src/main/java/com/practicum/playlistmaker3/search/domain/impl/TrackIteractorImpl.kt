package com.practicum.playlistmaker3.search.domain.impl


import com.practicum.playlistmaker3.search.domain.impl.api.TrackIteractor
import com.practicum.playlistmaker3.search.domain.impl.api.TrackRepository
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrackIteractorImpl(private val repository: TrackRepository) : TrackIteractor {

    override  fun searchTrack(expression: String): Flow<Pair<List<Track>?, String?>> {
        return repository.searchTrack(expression).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, "")
                is Resource.Error -> Pair(null, result.message)
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