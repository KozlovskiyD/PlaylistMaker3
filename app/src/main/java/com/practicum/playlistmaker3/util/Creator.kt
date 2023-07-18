package com.practicum.playlistmaker3.util

import android.content.Context
import com.practicum.playlistmaker3.player.data.MediaRepositoryImpl
import com.practicum.playlistmaker3.player.data.mediaPlayer.MediaPlayers
import com.practicum.playlistmaker3.player.domain.setPreparePlayer.MediaRepository
import com.practicum.playlistmaker3.search.data.TrackRepositoryImpl
import com.practicum.playlistmaker3.search.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker3.search.data.sharedPreferences.SharedPreferences
import com.practicum.playlistmaker3.search.domain.api.TrackIteractor
import com.practicum.playlistmaker3.search.domain.api.TrackRepository
import com.practicum.playlistmaker3.search.domain.impl.TrackIteractorImpl
import com.practicum.playlistmaker3.settings.data.SaveThemeNightImpl
import com.practicum.playlistmaker3.settings.domain.SettingRepository
import com.practicum.playlistmaker3.sharing.data.SharingRepositoryImpl
import com.practicum.playlistmaker3.sharing.data.ToShare
import com.practicum.playlistmaker3.sharing.data.UserAgreement
import com.practicum.playlistmaker3.sharing.data.WriteToSupport
import com.practicum.playlistmaker3.sharing.domain.SharingRepository

object Creator {
    private fun getTrackRepository(context: Context): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient(context), SharedPreferences(context))
    }

    fun provideTrackIteractor(context: Context): TrackIteractor {
        return TrackIteractorImpl(getTrackRepository(context))
    }


    fun getSharingRepository(context: Context): SharingRepository {
        return SharingRepositoryImpl(ToShare(context),
            WriteToSupport(context),
            UserAgreement(context))
    }

    fun settingRepository(context: Context): SettingRepository {
        return SaveThemeNightImpl(context)
    }

    fun getMediaRepository(context: Context): MediaRepository {
        return MediaRepositoryImpl(context, MediaPlayers(context))
    }
}