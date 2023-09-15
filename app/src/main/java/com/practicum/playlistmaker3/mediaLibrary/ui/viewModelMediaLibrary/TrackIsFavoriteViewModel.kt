package com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker3.mediaLibrary.domain.db.IsFavoriteInteractor
import kotlinx.coroutines.launch

class TrackIsFavoriteViewModel(private val isFavoriteInteractor: IsFavoriteInteractor) :
    ViewModel() {

    private var favoriteLiveDataMutable = MutableLiveData<TrackFavoriteState>()
    fun observeState(): LiveData<TrackFavoriteState> = favoriteLiveDataMutable

    fun loadFavoriteList() {
        viewModelScope.launch {
            isFavoriteInteractor.isFavoriteTracks().collect { tracks ->
                if (tracks.isEmpty()) renderState(TrackFavoriteState.Empty)
                else renderState(TrackFavoriteState.Content(tracks))
            }
        }
    }

    private fun renderState(state: TrackFavoriteState) {
        favoriteLiveDataMutable.postValue(state)
    }
}