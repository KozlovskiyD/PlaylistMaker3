package com.practicum.playlistmaker3

import android.content.Intent
import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class TrackListAdapter(sharedPrefs: SharedPreferences) :

    RecyclerView.Adapter<TrackListViewHolder>() {
    private var tracks = mutableListOf<Track>()
    private var current = false
    private val searchHistory = SearchHistory(sharedPrefs)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListViewHolder =
        TrackListViewHolder(parent)

    override fun onBindViewHolder(holder: TrackListViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            searchHistory.saveHistory(tracks[position], current)
            Intent(it.context, MediaActivity::class.java).apply {
                it.context.startActivity(this)
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

    fun currentLists(currentList: Boolean) {
        current = currentList
    }
}