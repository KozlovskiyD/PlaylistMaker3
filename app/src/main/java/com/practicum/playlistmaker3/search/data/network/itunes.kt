package com.practicum.playlistmaker3.search.data.network

import com.practicum.playlistmaker3.search.data.dto.TrackSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Itunes {
    @GET("/search?entity=song")
    suspend fun search(@Query("term") text: String): TrackSearchResponse
}