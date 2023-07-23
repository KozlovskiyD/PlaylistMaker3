package com.practicum.playlistmaker3.search.data.network

import com.practicum.playlistmaker3.search.data.NetworkClient
import com.practicum.playlistmaker3.search.data.connecting.IsConnected
import com.practicum.playlistmaker3.search.data.dto.Response
import com.practicum.playlistmaker3.search.data.dto.TrackSearchRequest

@Suppress("UNREACHABLE_CODE")
class RetrofitNetworkClient(
    private val isConnect: IsConnected,
    private val trackServer: Itunes,
) : NetworkClient {

    override fun request(dto: Any): Response {
        if (!isConnect.connecting()) {
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