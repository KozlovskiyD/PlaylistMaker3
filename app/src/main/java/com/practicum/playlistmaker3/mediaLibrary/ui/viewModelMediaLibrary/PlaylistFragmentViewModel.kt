package com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker3.mediaLibrary.domain.db.PlaylistInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.search.ui.viewModelSearch.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlaylistFragmentViewModel(private val playlistInteractor: PlaylistInteractor) : ViewModel() {

    private var isClickAllowed = true
    private val preparePlaylist: Playlist = Playlist()
    private val clickDebounceLiveData = MutableLiveData<Boolean>()
    fun observeClickDebounce(): LiveData<Boolean> = clickDebounceLiveData

    fun collectingPlaylist(name: String?, description: String?, filePathDir: String?) {
        preparePlaylist.namePlaylist = name
        preparePlaylist.description = description
        preparePlaylist.filePath = filePathDir
        viewModelScope.launch {
            playlistInteractor.insertPlaylist(preparePlaylist)
        }
    }

    fun clickDebounce() {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(SearchViewModel.CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        clickDebounceLiveData.value = current
    }
}