package com.practicum.playlistmaker3.search.ui.viewActivity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker3.search.domain.models.Track


class TrackListAdapter(private var onTrackClick: (Track, Boolean) -> Unit) :
    RecyclerView.Adapter<TrackListViewHolder>() {
    private var tracks = mutableListOf<Track>()

    private var isCurrentListTrack = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListViewHolder =
        TrackListViewHolder(parent)

    override fun onBindViewHolder(holder: TrackListViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener { onTrackClick.invoke(tracks[position], false) }
        if (isCurrentListTrack) {
            holder.itemView.setOnLongClickListener {
                  onTrackClick.invoke(tracks[position], true)
                return@setOnLongClickListener true
            }
        }
    }
    override fun getItemCount(): Int {
        return tracks.size
    }

    fun setTracks(newTracks: List<Track>?, isScreen: Boolean) {
        isCurrentListTrack = isScreen
        tracks.clear()
        if (!newTracks.isNullOrEmpty()) {
            tracks.addAll(newTracks)
        }
        notifyDataSetChanged()
    }

}



