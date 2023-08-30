package com.practicum.playlistmaker3.search.ui.viewModelSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker3.search.domain.impl.api.TrackIteractor
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST", "UNUSED_EXPRESSION")
class SearchViewModel(private val trackIteractor: TrackIteractor) : ViewModel() {

    private var loadingObserver: ((Boolean) -> Unit)? = null

    private val stateLiveData = MutableLiveData<TracksSearchState>()
    fun observeState(): LiveData<TracksSearchState> = stateLiveData

    private val showToast = SingleLiveEvent<String>()
    fun observeShowToast(): LiveData<String> = showToast

    private var latestSearchText: String? = null
    private var searchJob: Job? = null

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        this.latestSearchText = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY)
            requestOnListTrack(changedText)
        }
    }

    fun saveHistory(saveList: Track) {
        trackIteractor.savePref(saveList)
    }

    fun loadHistory() {
        val listHistory = trackIteractor.loadPref()
        renderState(TracksSearchState.History(listHistory))
    }

    fun clearHistory() {
        trackIteractor.clearPref()
    }

    private fun renderState(state: TracksSearchState) {
        stateLiveData.postValue(state)
    }

    fun requestOnListTrack(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(TracksSearchState.Loading)

            viewModelScope.launch {
                trackIteractor.searchTrack(newSearchText).collect { pair ->
                    processResult(pair.first, pair.second)
                }
            }
        }
    }

    private fun processResult(foundTrack: List<Track>?, additionalMessage: String?){
        when (additionalMessage) {
            "false" -> {
                renderState(TracksSearchState.Error)
            }
            "true" -> {
                renderState(
                    TracksSearchState.Empty)
            }
            else -> {
                val currentTracks = mutableListOf<Track>().also {
                    it.addAll(foundTrack!!)
                }
                renderState(TracksSearchState.Content(currentTracks = currentTracks))
            }
        }
    }

    fun removeLoadingObserver() {
        this.loadingObserver = null
    }

    companion object {
        const val SEARCH_DELAY = 2000L
    }
}