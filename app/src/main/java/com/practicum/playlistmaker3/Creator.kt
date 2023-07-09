package com.practicum.playlistmaker3

import com.practicum.playlistmaker3.search.data.TrackRepositoryImpl
import com.practicum.playlistmaker3.search.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker3.search.domain.api.TrackIteractor
import com.practicum.playlistmaker3.search.domain.api.TrackRepository
import com.practicum.playlistmaker3.search.domain.impl.TrackIteractorImpl

object Creator {
    private fun getTrackRepository(): TrackRepository{
        return TrackRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTrackIteractor(): TrackIteractor{
        return TrackIteractorImpl(getTrackRepository())
    }
}