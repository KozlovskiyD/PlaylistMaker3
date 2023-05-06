package com.practicum.playlistmaker3

import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class TrackListAdapter(private val sharedPrefs: SharedPreferences) :
    RecyclerView.Adapter<TrackListViewHolder>() {
    private var tracks = mutableListOf<Track>()
    private val searchActivity = SearchActivity()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListViewHolder =
        TrackListViewHolder(parent)

    override fun onBindViewHolder(holder: TrackListViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            searchActivity.saveHistory(sharedPrefs, tracks[position])
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