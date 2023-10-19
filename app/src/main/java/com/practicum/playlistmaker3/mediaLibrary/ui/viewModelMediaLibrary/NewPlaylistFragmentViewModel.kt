package com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker3.mediaLibrary.domain.api.PlaylistInteractor
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.states.ListPlaylistState
import kotlinx.coroutines.launch

class NewPlaylistFragmentViewModel(private val playlistInteractor: PlaylistInteractor) :
    ViewModel() {

    private var playlistLiveDataMutable = MutableLiveData<ListPlaylistState>()
    fun observeState(): LiveData<ListPlaylistState> = playlistLiveDataMutable

    private var listLiveDataMutable = MutableLiveData<String>()
    fun observeStateList(): LiveData<String> = listLiveDataMutable

    fun loadListPlaylist() {
        viewModelScope.launch {
            playlistInteractor.getPlaylist().collect { ListPlaylist ->
                if (ListPlaylist.isEmpty()) renderState(ListPlaylistState.Empty)
                else renderState(ListPlaylistState.Content(ListPlaylist))
            }
        }
    }

    private fun renderState(state: ListPlaylistState) {
        playlistLiveDataMutable.postValue(state)
    }
}