package com.practicum.playlistmaker3.data.network

import com.practicum.playlistmaker3.data.dto.DataTrackResponse
import  retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Itunes {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<DataTrackResponse>

}