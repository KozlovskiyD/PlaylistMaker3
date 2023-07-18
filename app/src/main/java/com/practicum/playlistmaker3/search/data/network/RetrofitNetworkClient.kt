package com.practicum.playlistmaker3.search.data.network

import android.content.Context
import com.practicum.playlistmaker3.search.data.NetworkClient
import com.practicum.playlistmaker3.search.data.connecting.IsConnected
import com.practicum.playlistmaker3.search.data.dto.Response
import com.practicum.playlistmaker3.search.data.dto.TrackSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("UNREACHABLE_CODE")
class RetrofitNetworkClient(context: Context) : NetworkClient {

    private val isConnect = IsConnected(context)

    private val baseUrl = "http://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackServer = retrofit.create(Itunes::class.java)

    override fun request(dto: Any): Response {
        val isConnected = isConnect.connecting()
        if (!isConnected) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is TrackSearchRequest) {
            return Response().apply { resultCode = 400 }
        }
        val resp = trackServer.search(text = dto.expression).execute()
        val body = resp.body()
        return if (body?.results?.isNotEmpty() == true) {
            body.apply { resultCode = resp.code() }
        } else {
            Response().apply { resultCode = 0 }
        }
    }
}