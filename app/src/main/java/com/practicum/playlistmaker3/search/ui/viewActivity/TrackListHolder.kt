package com.practicum.playlistmaker3.search.ui.viewActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.search.domain.models.getCoverArtwork60
import com.practicum.playlistmaker3.util.simpleDateFormat


class TrackListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.track, parent, false)
) {

    private val imageTracks: ImageView = itemView.findViewById(R.id.imageTrack)
    private val tvTractName: TextView = itemView.findViewById(R.id.tvTrackName)
    private val tvArtistName: TextView = itemView.findViewById(R.id.tvArtistName)
    private val tvTractTime: TextView = itemView.findViewById(R.id.tvTrackTime)

    fun bind(model: Track) {
        Glide
            .with(itemView)
            .load(getCoverArtwork60(model.artworkUrl100))
            .placeholder(R.drawable.vector_placeholder)
            .centerCrop()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.top_2)))
            .into(imageTracks)

        tvTractName.text = model.trackName
        tvArtistName.text = model.artistName
        tvTractTime.text = simpleDateFormat(model.trackTimeMillis)
    }
}


