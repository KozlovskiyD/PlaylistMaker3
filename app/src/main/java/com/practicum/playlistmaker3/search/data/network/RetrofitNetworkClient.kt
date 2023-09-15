package com.practicum.playlistmaker3.search.data.network

import com.practicum.playlistmaker3.search.data.NetworkClient
import com.practicum.playlistmaker3.search.data.connecting.IsConnected
import com.practicum.playlistmaker3.search.data.dto.Response
import com.practicum.playlistmaker3.search.data.dto.TrackSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("UNREACHABLE_CODE")
class RetrofitNetworkClient(
    private val isConnect: IsConnected,
    private val trackServer: Itunes,
) : NetworkClient {

    override suspend fun request(dto: Any): Response {
        if (!isConnect.connecting()) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is TrackSearchRequest) {
            return Response().apply { resultCode = 400 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = trackServer.search(text = dto.expression)
                if (response.results.isEmpty()) Response().apply { resultCode = 500 }
                else response.apply {  resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }
}