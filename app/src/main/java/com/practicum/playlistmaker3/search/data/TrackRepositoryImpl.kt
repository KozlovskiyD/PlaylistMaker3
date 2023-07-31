package com.practicum.playlistmaker3.search.data


import com.practicum.playlistmaker3.search.data.dto.TrackDto
import com.practicum.playlistmaker3.search.data.dto.TrackSearchRequest
import com.practicum.playlistmaker3.search.data.dto.TrackSearchResponse
import com.practicum.playlistmaker3.search.domain.impl.api.TrackRepository
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.util.Resource

class TrackRepositoryImpl(
    private val networkClient: NetworkClient,
    private val sharedPrefClient: SharedPrefClient,
) : TrackRepository {

    override fun searchTrack(expression: String): Resource<List<Track>> {
        val response = networkClient.request(TrackSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> Resource.Error("false")
            200 -> {
                Resource.Success((response as TrackSearchResponse).results.map {
                    Track(it.trackId,
                        it.trackName,
                        it.artistName,
                        it.trackTimeMillis,
                        it.artworkUrl100,
                        it.collectionName,
                        it.releaseDate,
                        it.primaryGenreName,
                        it.country,
                        it.previewUrl)
                }
                )
            }
            else -> Resource.Error("true")
        }
    }

    override fun savePref(saveTrack: Track) {
        sharedPrefClient.savePref(trackToTrackDto(saveTrack))
    }

    override fun loadPref(): List<Track> {
        return trackDtoToTrack(sharedPrefClient.loadPref())
    }

    override fun clearPref() {
        sharedPrefClient.clearPref()
    }

    private fun trackToTrackDto(track: Track): TrackDto {
        return TrackDto(
            trackId = track.trackId,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl
        )
    }

    private fun trackDtoToTrack(list: List<TrackDto>): List<Track> {
        return list.map {
            Track(it.trackId,
                it.trackName,
                it.artistName,
                it.trackTimeMillis,
                it.artworkUrl100,
                it.collectionName,
                it.releaseDate,
                it.primaryGenreName,
                it.country,
                it.previewUrl
            )
        }
    }
}