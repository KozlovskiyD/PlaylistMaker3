package com.practicum.playlistmaker3.util

import android.content.Context
import com.practicum.playlistmaker3.player.data.MediaRepositoryImpl
import com.practicum.playlistmaker3.player.data.mediaPlayer.MediaPlayers
import com.practicum.playlistmaker3.player.domain.api.MediaIteractor
import com.practicum.playlistmaker3.player.domain.api.MediaRepository
import com.practicum.playlistmaker3.player.domain.impl.MediaIteractorImpl
import com.practicum.playlistmaker3.search.data.TrackRepositoryImpl
import com.practicum.playlistmaker3.search.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker3.search.data.sharedPreferences.SharedPreferences
import com.practicum.playlistmaker3.search.domain.api.TrackIteractor
import com.practicum.playlistmaker3.search.domain.api.TrackRepository
import com.practicum.playlistmaker3.search.domain.impl.TrackIteractorImpl
import com.practicum.playlistmaker3.settings.data.SaveThemeNight
import com.practicum.playlistmaker3.settings.data.SettingRepositoryImpl
import com.practicum.playlistmaker3.settings.domain.api.SettingIteractor
import com.practicum.playlistmaker3.settings.domain.api.SettingRepository
import com.practicum.playlistmaker3.settings.domain.impl.SettingIteractorImpl
import com.practicum.playlistmaker3.sharing.data.SharingRepositoryImpl
import com.practicum.playlistmaker3.sharing.data.ToShare
import com.practicum.playlistmaker3.sharing.data.UserAgreement
import com.practicum.playlistmaker3.sharing.data.WriteToSupport
import com.practicum.playlistmaker3.sharing.domain.api.SharingIteractor
import com.practicum.playlistmaker3.sharing.domain.api.SharingRepository
import com.practicum.playlistmaker3.sharing.domain.impl.SharingIteractorImpl

object Creator {
    private fun getTrackRepository(context: Context): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient(context), SharedPreferences(context))
    }

    fun provideTrackIteractor(context: Context): TrackIteractor {
        return TrackIteractorImpl(getTrackRepository(context))
    }


    private fun getSharingRepository(context: Context): SharingRepository {
        return SharingRepositoryImpl(ToShare(context),
            WriteToSupport(context),
            UserAgreement(context))
    }

    fun provideSharingIteractor(context: Context): SharingIteractor {
        return SharingIteractorImpl(getSharingRepository(context))
    }


    private fun getSettingRepository(context: Context): SettingRepository {
        return SettingRepositoryImpl(SaveThemeNight(context))
    }

    fun provideSettingIterator(context: Context): SettingIteractor {
        return SettingIteractorImpl(getSettingRepository(context))
    }


    private fun getMediaRepository(context: Context): MediaRepository {
        return MediaRepositoryImpl(context, MediaPlayers(context))
    }

    fun provideMediaIteractor(context: Context): MediaIteractor {
        return MediaIteractorImpl(getMediaRepository(context))
    }
}