package com.practicum.playlistmaker3.di

import com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor.PlaylistDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor.PlaylistTrackDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor.TrackDbConvertor
import com.practicum.playlistmaker3.mediaLibrary.data.FavoriteRepositoryImpl
import com.practicum.playlistmaker3.mediaLibrary.data.PlaylistRepositoryImpl
import com.practicum.playlistmaker3.mediaLibrary.domain.db.FavoriteRepository
import com.practicum.playlistmaker3.mediaLibrary.domain.db.PlaylistRepository
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
        TrackDbConvertor
    }

    factory {
        PlaylistDbConvertor(get())
    }

    factory {
        PlaylistTrackDbConvertor
    }

    single<FavoriteRepository> {
        FavoriteRepositoryImpl(get(), get())
    }

    single<PlaylistRepository> {
        PlaylistRepositoryImpl(get(), get(), get())
    }
}