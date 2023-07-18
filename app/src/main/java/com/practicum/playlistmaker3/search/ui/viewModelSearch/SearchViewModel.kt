package com.practicum.playlistmaker3.search.ui.viewModelSearch

import android.app.Application
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker3.search.domain.api.TrackIteractor
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.util.Creator

@Suppress("UNCHECKED_CAST", "UNUSED_EXPRESSION")
class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private var loadingObserver: ((Boolean) -> Unit)? = null
    private val trackIteractor = Creator.provideTrackIteractor(getApplication())
    private val handler = android.os.Handler(Looper.getMainLooper())

    private val stateLiveData = MutableLiveData<TracksSearchState>()
    fun observeState(): LiveData<TracksSearchState> = stateLiveData

    private val showToast = SingleLiveEvent<String>()
    fun observeShowToast(): LiveData<String> = showToast

    private var latestSearchText: String? = null

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { requestOnListTrack(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DELAY
        handler.postAtTime(searchRunnable, SEARCH_REQUEST_TOKEN, postTime)
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

            trackIteractor.searchTrack(newSearchText, object : TrackIteractor.TrackConsumer {
                override fun consume(foundTrack: List<Track>?, additionalMessage: String?) {

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
            })
        }
    }

    fun removeLoadingObserver() {
        this.loadingObserver = null
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    companion object {
        private val SEARCH_REQUEST_TOKEN = Any()
        const val SEARCH_DELAY = 2000L
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }
}