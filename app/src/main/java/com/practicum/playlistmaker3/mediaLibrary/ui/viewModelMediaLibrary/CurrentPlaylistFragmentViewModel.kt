package com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker3.mediaLibrary.domain.api.PlaylistInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.util.getTrackMinutes
import com.practicum.playlistmaker3.util.getTrackNumber
import com.practicum.playlistmaker3.util.simpleDateFormat
import com.practicum.playlistmaker3.util.simpleDateFormatMinute
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CurrentPlaylistFragmentViewModel(private val playlistInteractor: PlaylistInteractor) :
    ViewModel() {

    var playlistJob: Job? = null

    private var trackListLiveDataMutable = MutableLiveData<List<Track>>()
    fun observeState(): LiveData<List<Track>> = trackListLiveDataMutable

    private var timeTrackLiveDataMutable = MutableLiveData<String>()
    fun observeStateTime(): LiveData<String> = timeTrackLiveDataMutable

    private var numberTrackLiveDataMutable = MutableLiveData<String>()
    fun observeStateTrack(): LiveData<String> = numberTrackLiveDataMutable

    private var messageTrackLiveDataMutable = MutableLiveData<String>()
    fun observeStateMessage(): LiveData<String> = messageTrackLiveDataMutable

    private var exitDataMutable = MutableLiveData<Boolean>()
    fun observeStateExit(): LiveData<Boolean> = exitDataMutable

    fun loadListTrack(trackList: List<Long>) {
        viewModelScope.launch {
            playlistInteractor.getListTrack(trackList).collect { listTrack ->
                totalTimeTrack(listTrack)
                trackListLiveDataMutable.postValue(listTrack)
            }
        }
    }

    fun deleteTrack(track: Track, playlistId: Int) {
        viewModelScope.launch {
            loadListTrack(playlistInteractor.deleteTrackPlaylist(track, playlistId))
        }
    }

    private fun totalTimeTrack(listTrack: List<Track>) {
        var totalTime = 0L
        listTrack.indices.forEach { i ->
            totalTime += listTrack[i].trackTimeMillis.toLong()
        }
        val totalMinutes = simpleDateFormatMinute(totalTime.toString())
        val totalTimeTrack =
            String.format("%s %s", totalMinutes, getTrackMinutes(totalMinutes.toInt()))
        timeTrackLiveDataMutable.postValue(totalTimeTrack)
        val total = String.format("%d %s", listTrack.size, getTrackNumber(listTrack.size))
        numberTrackLiveDataMutable.postValue(total)
    }

    fun deleteTracksPlaylist(playlist: Playlist) {
        playlistJob?.cancel()
        viewModelScope.launch {
            playlistInteractor.deletePlaylist(playlist)
        }
        exitDataMutable.postValue(true)
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            playlistInteractor.deletePlaylist(playlist)
        }
        exitDataMutable.postValue(false)
    }

    fun showMessage(playlist: Playlist) {
        viewModelScope.launch {
            playlistInteractor.getListTrackShare(playlist).collect { listTrackShare ->
                if (listTrackShare.isNotEmpty()) {
                    var message: String
                    var timeTrack: String
                    var number = 1
                    val numberTrack = String.format(
                        "%02d %s", listTrackShare.size, getTrackNumber(listTrackShare.size)
                    )
                    var totalMessage =
                        "${playlist.namePlaylist}\n${playlist.description}\n${numberTrack}\n"
                    for (item in listTrackShare) {
                        timeTrack = simpleDateFormat(item.trackTimeMillis)
                        message =
                            "${number}. ${item.artistName} - ${item.trackName} (${timeTrack})\n"
                        totalMessage += message
                        number++
                    }
                    messageTrackLiveDataMutable.postValue(totalMessage)
                } else messageTrackLiveDataMutable.postValue("no")
            }
        }
    }
}