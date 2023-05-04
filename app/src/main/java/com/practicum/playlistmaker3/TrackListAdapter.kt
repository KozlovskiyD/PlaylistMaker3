package com.practicum.playlistmaker3

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackListAdapter() :
    RecyclerView.Adapter<TrackListViewHolder>() {

    private val tracks = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListViewHolder =
        TrackListViewHolder(parent)

    override fun onBindViewHolder(holder: TrackListViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            val saveTrack = tracks[position]
            Log.e("TAG", saveTrack.toString())
             SearchActivity().also {
                it.saveHistory(saveTrack)
            }
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    fun setTracks(newTracks: List<Track>?) {
        tracks.clear()
        if (!newTracks.isNullOrEmpty()) {
            tracks.addAll(newTracks)
        }
        notifyDataSetChanged()
    }
}