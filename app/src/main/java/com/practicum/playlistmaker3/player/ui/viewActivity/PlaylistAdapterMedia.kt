package com.practicum.playlistmaker3.player.ui.viewActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist

class PlaylistAdapterMedia(private var onClickPlaylistItem: (Playlist) -> Unit) :
    RecyclerView.Adapter<PlaylistViewHolderMedia>() {

    private val playlistItems = mutableListOf<Playlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolderMedia {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_item_media, parent, false)
        return PlaylistViewHolderMedia(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolderMedia, position: Int) {
        holder.bind(playlistItems[position])
        holder.itemView.setOnClickListener {
            onClickPlaylistItem.invoke(playlistItems[position])
        }
    }

    override fun getItemCount(): Int {
        return playlistItems.size
    }

    fun setPlaylistItem(newPlaylistItems: List<Playlist>?) {
        playlistItems.clear()
        if (!newPlaylistItems.isNullOrEmpty()) {
            playlistItems.addAll(newPlaylistItems)
        }
        notifyDataSetChanged()
    }
}