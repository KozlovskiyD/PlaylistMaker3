package com.practicum.playlistmaker3.player.ui.viewModelMediaPlayer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker3.player.data.TrackRepositoryImpl
import com.practicum.playlistmaker3.player.data.createTime.CreateTimeImpl
import com.practicum.playlistmaker3.player.data.mediaPlayer.MediaPlayers
import com.practicum.playlistmaker3.player.domain.getCreateTime.GetCreateTimeUC
import com.practicum.playlistmaker3.player.domain.setPreparePlayer.SetTrackUC

@Suppress("UNCHECKED_CAST")
class TrackViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val mediaPlayers by lazy { MediaPlayers(context) }
    private val trackRepositoryImpl by lazy {
        TrackRepositoryImpl(context, mediaPlayers)
    }
    private val setTrackUC by lazy { SetTrackUC(trackRepositoryImpl) }
    private val createTimeImpl by lazy { CreateTimeImpl(mediaPlayers) }
    private val getCreateTimeUC by lazy { GetCreateTimeUC(createTimeImpl) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrackViewModel(setTrackUC, getCreateTimeUC) as T
    }
}