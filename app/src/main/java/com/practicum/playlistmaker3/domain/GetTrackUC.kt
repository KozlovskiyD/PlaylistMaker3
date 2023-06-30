package com.practicum.playlistmaker3.domain

import android.content.Intent
import com.practicum.playlistmaker3.domain.models.Track


@Suppress("CAST_NEVER_SUCCEEDS")
class GetTrackUC {
    fun execute(intent: Intent): Track {
        return intent.getSerializableExtra("track") as Track
    }
}