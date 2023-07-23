package com.practicum.playlistmaker3.player.data.SharedPrefs

import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmaker3.search.data.dto.TrackDto

const val KEY_PREVIEW = "keyPreview"

class SharedPrefs(private var json: Gson, private val sharedPrefs: SharedPreferences) {

    fun sharedPref(currentTrackDto: TrackDto) {
        sharedPrefs.edit().putString(KEY_PREVIEW, json.toJson(currentTrackDto).toString()).apply()
    }
}