package com.practicum.playlistmaker3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackListAdapter (private val tracks: List<Tract>): RecyclerView.Adapter<TrackListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track, parent, false)
        return TrackListViewHolder(view)
    }
    override fun onBindViewHolder(holder: TrackListViewHolder, position: Int) {
        holder.bind(tracks[position])
    }
    override fun getItemCount(): Int {
        return tracks.size
    }
}