package com.practicum.playlistmaker3

import  retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Itunes {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<DataTrackResponse>

}