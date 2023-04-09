package com.practicum.playlistmaker3

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val imageTracks: ImageView = itemView.findViewById(R.id.imageTrack)
    private val tvTractName: TextView = itemView.findViewById(R.id.tvTrackName)
    private val tvArtistName: TextView = itemView.findViewById(R.id.tvArtistName)
    private val tvTractTime: TextView = itemView.findViewById(R.id.tvTrackTime)

    fun bind(model: Tract) {
        tvTractName.text = model.trackName
        tvArtistName.text = model.artistName
        tvTractTime.text = model.trackTime

        Glide
            .with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .transform(RoundedCorners(10))
            .into(imageTracks)
    }
}