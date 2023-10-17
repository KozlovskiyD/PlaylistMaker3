package com.practicum.playlistmaker3.player.ui.viewActivity

import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity.PlayListFragment
import com.practicum.playlistmaker3.util.getTrackNumber
import java.io.File

class PlaylistViewHolderMedia(view: View) : RecyclerView.ViewHolder(view) {

    private val image: ImageView = itemView.findViewById(R.id.image_bottom_sheet_media)
    private val name: TextView = itemView.findViewById(R.id.text_name_playlist_bottom_sheet)
    private val descriptor: TextView = itemView.findViewById(R.id.text_number_tracks_bottom_sheet)

    fun bind(model: Playlist) {
        val filePath = File(
            itemView.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            PlayListFragment.PICTURES_DIR
        )

        Glide
            .with(itemView)
            .load(model.filePath?.let { File(filePath, it) })
            .placeholder(R.drawable.vector_placeholder)
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.top_2)))
            .into(image)
        name.text = model.namePlaylist
        descriptor.text = String.format("%d %s", model.trackCount, getTrackNumber(model.trackCount))
    }
}