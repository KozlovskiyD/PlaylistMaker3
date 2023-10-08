package com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity

import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.util.getTrackNumber
import java.io.File

class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val image: ImageView = itemView.findViewById(R.id.image_playlist_placeholder)
    private val name: TextView = itemView.findViewById(R.id.text_name_playlist)
    private val numberTrack: TextView = itemView.findViewById(R.id.text_description_playlist)


    fun bind(model: Playlist) {
        val filePath = File(
            itemView.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            PlayListFragment.PICTURES_DIR
        )

        Glide
            .with(itemView)
            .load(model.filePath?.let { File(filePath, it) })
            .placeholder(R.drawable.vector_placeholder_100)
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.top_8)))
            .into(image)
        name.text = model.namePlaylist
        numberTrack.text =
            String.format("%d %s", model.trackCount, getTrackNumber(model.trackCount))
    }
}