package com.practicum.playlistmaker3.search.data

import com.practicum.playlistmaker3.search.data.dto.TrackSearchRequest
import com.practicum.playlistmaker3.search.data.dto.TrackSearchResponse
import com.practicum.playlistmaker3.search.domain.api.TrackRepository
import com.practicum.playlistmaker3.search.domain.models.Track

class TrackRepositoryImpl(private val networkClient: NetworkClient): TrackRepository {

    override fun searchTrack(expression: String): List<Track> {
        val response = networkClient.request(TrackSearchRequest(expression))
        return if (response.resultCode == 200){
            (response as TrackSearchResponse).results.map {
                Track(it.trackId, it.trackName, it.artistName, it.trackTimeMillis, it.artworkUrl100, it.collectionName, it.releaseDate, it.primaryGenreName, it.country, it.previewUrl)
            }
        } else {
            emptyList()
        }
    }
}