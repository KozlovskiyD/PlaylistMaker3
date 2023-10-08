package com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist

class PlaylistAdapter(private val playlistItems: List<Playlist>) :
    RecyclerView.Adapter<PlaylistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlistItems[position])
    }

    override fun getItemCount(): Int {
        return playlistItems.size
    }
}