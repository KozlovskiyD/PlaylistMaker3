package com.practicum.playlistmaker3.di

import com.practicum.playlistmaker3.main.ui.viewModel.MainViewModel
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.NewPlaylistFragmentViewModel
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.PlaylistFragmentViewModel
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.TrackIsFavoriteViewModel
import com.practicum.playlistmaker3.player.ui.viewModelMediaPlayer.TrackViewModel
import com.practicum.playlistmaker3.search.ui.viewModelSearch.SearchViewModel
import com.practicum.playlistmaker3.settings.ui.viewModelSettings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel(get())
    }

    viewModel<TrackViewModel> {
        TrackViewModel(get(), get(), get())
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(get(), get())
    }

    viewModel<MainViewModel> {
        MainViewModel(get())
    }
    viewModel<NewPlaylistFragmentViewModel> {
        NewPlaylistFragmentViewModel(get())
    }
    viewModel<TrackIsFavoriteViewModel> {
        TrackIsFavoriteViewModel(get())
    }
    viewModel<PlaylistFragmentViewModel> {
        PlaylistFragmentViewModel(get())
    }
}