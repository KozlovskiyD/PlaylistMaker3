package com.practicum.playlistmaker3.player.data.SharedPrefs

import android.content.Context
import com.google.gson.Gson
import com.practicum.playlistmaker3.search.data.dto.TrackDto

const val PREVIEW_URL = "previewUrl"
const val KEY_PREVIEW = "keyPreview"

class SharedPrefs(context: Context) {

    private var sharedPrefs = context.getSharedPreferences(PREVIEW_URL, Context.MODE_PRIVATE)

    fun sharedPref(currentTrackDto: TrackDto) {
        val json = Gson().toJson(currentTrackDto)
        sharedPrefs.edit()
            .putString(KEY_PREVIEW, json.toString())
            .apply()
    }
}