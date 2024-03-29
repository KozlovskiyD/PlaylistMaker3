package com.practicum.playlistmaker3.di

import com.practicum.playlistmaker3.mediaLibrary.domain.api.FavoriteInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.api.PlaylistInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.impl.FavoriteInteractorImpl
import com.practicum.playlistmaker3.mediaLibrary.domain.impl.PlaylistInteractorImpl
import com.practicum.playlistmaker3.player.domain.api.MediaIteractor
import com.practicum.playlistmaker3.player.domain.impl.MediaIteractorImpl
import com.practicum.playlistmaker3.search.domain.impl.TrackIteractorImpl
import com.practicum.playlistmaker3.search.domain.impl.api.TrackIteractor
import com.practicum.playlistmaker3.settings.domain.api.SettingIteractor
import com.practicum.playlistmaker3.settings.domain.impl.SettingIteractorImpl
import com.practicum.playlistmaker3.sharing.domain.api.SharingIteractor
import com.practicum.playlistmaker3.sharing.domain.impl.SharingIteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<TrackIteractor> {
        TrackIteractorImpl(get())
    }

    factory<MediaIteractor> {
        MediaIteractorImpl(get())
    }

    single<SettingIteractor> {
        SettingIteractorImpl(get())
    }

    single<SharingIteractor> {
        SharingIteractorImpl(get())
    }

    single<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    single<PlaylistInteractor> {
        PlaylistInteractorImpl(get())
    }
}