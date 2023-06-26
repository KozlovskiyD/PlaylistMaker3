package com.practicum.playlistmaker3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class TrackListAdapter(sharedPrefs: SharedPreferences) :

    RecyclerView.Adapter<TrackListViewHolder>() {
    private var tracks = mutableListOf<Track>()
    private var current = false
    private val searchHistory = SearchHistory(sharedPrefs)
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListViewHolder =
        TrackListViewHolder(parent)

    override fun onBindViewHolder(holder: TrackListViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            searchHistory.saveHistory(tracks[position], current)
            if (clickDebounce()) {
                Intent(it.context, MediaActivity::class.java).apply {
                    it.context.startActivity(this)
                }
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

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, SearchActivity.CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
}