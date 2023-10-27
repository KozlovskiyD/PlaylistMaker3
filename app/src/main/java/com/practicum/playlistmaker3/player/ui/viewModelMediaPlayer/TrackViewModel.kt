package com.practicum.playlistmaker3.player.ui.viewModelMediaPlayer

import android.annotation.SuppressLint
import android.app.usage.NetworkStats.Bucket.STATE_DEFAULT
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker3.mediaLibrary.domain.api.FavoriteInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.api.PlaylistInteractor
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.player.domain.api.MediaIteractor
import com.practicum.playlistmaker3.player.domain.screenModel.ScreenMediaModel
import com.practicum.playlistmaker3.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class TrackViewModel(
    private val mediaIteractor: MediaIteractor,
    private val favoriteInteractor: FavoriteInteractor,
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    private var mediaLiveDataMutable = MutableLiveData<ScreenMediaModel>()
    var mediaLiveData: LiveData<ScreenMediaModel> = mediaLiveDataMutable

    private var isFavoriteDataMutable = MutableLiveData<ScreenMediaModel>()
    var isFavoriteLiveData: LiveData<ScreenMediaModel> = isFavoriteDataMutable

    private var playlistLiveDataMutable = MutableLiveData<List<Playlist>>()
    var playlistLiveData: LiveData<List<Playlist>> = playlistLiveDataMutable

    private var addTrackInPlaylistDataMutable = MutableLiveData<Boolean>()
    var addTrackInPlaylistLiveData: LiveData<Boolean> = addTrackInPlaylistDataMutable

    private var playerState = STATE_DEFAULT
    private var timerJob: Job? = null
    var duration = 0L
    var currentSecond = 0L
    var screen = ScreenMediaModel(false, 0L, false)

    fun preparePlayer(currentTrackPreviewUrl: Track) {
        playerState = STATE_PREPARED
        mediaIteractor.sendTrack(currentTrackPreviewUrl)
    }

    fun startCreateTime() {
        duration = (mediaIteractor.getCurrentTime(true))
        timerJob = viewModelScope.launch {
            while (playerState == STATE_PLAYING) {
                delay(STATE_CREATE_TIME)
                currentSecond = (mediaIteractor.getCurrentTime(false))
                screen.time = currentSecond
                mediaLiveDataMutable.value = screen
                if (currentSecond >= duration) {
                    screen.playButton = true
                    screen.time = 0L
                    mediaLiveDataMutable.value = screen
                    playerState = STATE_PREPARED
                }
            }
        }
    }

    fun playbackControl() {
        mediaIteractor.controlPlayState(playerState)
        when (playerState) {
            STATE_PLAYING -> playerPause()
            STATE_PREPARED, STATE_PAUSED -> playerStart()
        }
    }

    private fun playerStart() {
        screen.playButton = false
        mediaLiveDataMutable.value = screen
        playerState = STATE_PLAYING
    }

    private fun playerPause() {
        timerJob?.cancel()
        screen.playButton = true
        mediaLiveDataMutable.value = screen
        playerState = STATE_PAUSED
    }

    fun playerStateChange(state: Int) {
        playerState = state
        playbackControl()
    }

    fun onFavoriteClicked(track: Track) {
        viewModelScope.launch {
            if (track.isFavorite) favoriteInteractor.insertTrack(track)
            else favoriteInteractor.deleteTrack(track)
        }
    }

    fun favoriteTrackListId(trackId: String) {
        viewModelScope.launch {
            favoriteInteractor.favoriteTrackListId().collect { tracks ->
                for (item in tracks) {
                    if (item.trackId == trackId) {
                        screen.isFavorite = true
                        isFavoriteDataMutable.value = screen
                        break
                    }
                }
            }
        }
    }

    fun loadListPlaylist() {
        viewModelScope.launch {
            playlistInteractor.getPlaylist().collect { listPlaylist ->
                playlistLiveDataMutable.value = listPlaylist
            }
        }
    }

    fun saveTrackInPlaylist(playlist: Playlist, track: Track) {
        val isAddTrack = playlist.trackList.contains(track.trackId.toLong())
        if (isAddTrack) addTrackInPlaylistDataMutable.value = true
        else {
            viewModelScope.launch {
                playlistInteractor.insertPlaylistTrack(playlist, track)
            }
            addTrackInPlaylistDataMutable.value = false
        }
    }

    companion object {
        private const val STATE_CREATE_TIME = 300L
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
}