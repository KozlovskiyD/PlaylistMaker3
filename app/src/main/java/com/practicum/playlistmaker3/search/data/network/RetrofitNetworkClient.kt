package com.practicum.playlistmaker3.search.data.network

import com.practicum.playlistmaker3.search.data.NetworkClient
import com.practicum.playlistmaker3.search.data.dto.Response
import com.practicum.playlistmaker3.search.data.dto.TrackSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient: NetworkClient {

    private val baseUrl = "http://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackServer = retrofit.create(Itunes::class.java)

    override fun request(dto: Any): Response {
        if (dto is TrackSearchRequest){
            val resp = trackServer.search(dto.expression).execute()
            val body = resp.body()?: Response()
            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}