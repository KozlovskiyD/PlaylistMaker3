package com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker3.mediaLibrary.domain.api.PlaylistInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import kotlinx.coroutines.launch

class EditablePlaylistFragmentViewModel(private val playlistInteractor: PlaylistInteractor): PlaylistFragmentViewModel(playlistInteractor) {

    private var editPlaylistLiveDataMutable = MutableLiveData<Playlist>()
    fun observeState(): LiveData<Playlist> = editPlaylistLiveDataMutable

    private var exitPlaylistLiveDataMutable = MutableLiveData<Boolean>()
    fun exitObserveState(): LiveData<Boolean> = exitPlaylistLiveDataMutable

    fun loadPlaylist(playlist: Playlist){
        editPlaylistLiveDataMutable.postValue(playlist)
    }

    fun editPlaylist(playlist: Playlist){
        viewModelScope.launch {
            playlistInteractor.editPlaylist(playlist)
        }
        exitPlaylistLiveDataMutable.postValue(true)
    }
}