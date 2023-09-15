package com.practicum.playlistmaker3.search.data

import com.practicum.playlistmaker3.search.data.dto.Response

interface NetworkClient {
    suspend fun request(dto: Any): Response
}