package com.practicum.playlistmaker3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

data class Tract(val trackName: String, val artistName: String, val trackTime: String, val artworkUrl100: String)

class TrackListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val imageTracks: ImageView = itemView.findViewById(R.id.imageTrack)
    private val tvTractName: TextView = itemView.findViewById(R.id.tvTrackName)
    private val tvArtistName: TextView = itemView.findViewById(R.id.tvArtistName)
    private val tvTractTime: TextView = itemView.findViewById(R.id.tvTrackTime)

    fun bind(model: Tract) {
        model.trackName.also { tvTractName.text = it }
        model.artistName.also { tvArtistName.text =it }
        model.trackTime.also { tvTractTime.text = it }

        Glide
            .with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .transform(RoundedCorners(10))
            .into(imageTracks)
    }
}

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