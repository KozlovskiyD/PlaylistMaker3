package com.practicum.playlistmaker3.di

import com.practicum.playlistmaker3.mediaLibrary.data.IsFavoriteRepositoryImpl
import com.practicum.playlistmaker3.mediaLibrary.data.TrackDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.domain.db.IsFavoriteRepository
import com.practicum.playlistmaker3.player.data.MediaRepositoryImpl
import com.practicum.playlistmaker3.player.domain.api.MediaRepository
import com.practicum.playlistmaker3.search.data.TrackRepositoryImpl
import com.practicum.playlistmaker3.search.domain.impl.api.TrackRepository
import com.practicum.playlistmaker3.settings.data.SettingRepositoryImpl
import com.practicum.playlistmaker3.settings.domain.api.SettingRepository
import com.practicum.playlistmaker3.sharing.data.SharingRepositoryImpl
import com.practicum.playlistmaker3.sharing.domain.api.SharingRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<TrackRepository> {
        TrackRepositoryImpl(get(), get())
    }

    factory<MediaRepository> {
        MediaRepositoryImpl(get(), get())
    }

    single<SettingRepository> {
        SettingRepositoryImpl(get())
    }

    single<SharingRepository> {
        SharingRepositoryImpl(get(), get(), get())
    }

    factory {
        TrackDbConvertor()
    }

    single<IsFavoriteRepository> {
        IsFavoriteRepositoryImpl(get(), get())
    }
}