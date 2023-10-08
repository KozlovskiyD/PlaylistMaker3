package com.practicum.playlistmaker3.search.data.sharedPreferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker3.search.data.SharedPrefClient
import com.practicum.playlistmaker3.search.data.dto.TrackDto

class SharedPreferencesHistory(
    private var json: Gson,
    private val sharedPrefs: SharedPreferences,
) : SharedPrefClient {

    override fun loadPref(): List<TrackDto> {
        return saveHistory()
    }

    override fun savePref(saveTrack: TrackDto) {
        val listSave = saveHistory()
        var indexTrack = 10
        for (item in listSave) {
            if (item.trackId == saveTrack.trackId) indexTrack = (listSave.indexOf(item))
        }
        if (indexTrack < 10) listSave.removeAt(indexTrack)
        if (listSave.size >= 10) listSave.removeAt(index = 9)
        listSave.add(0, saveTrack)
        sharedPrefs.edit()
            .putString(KEY, json.toJson(listSave).toString())
            .apply()
    }

    override fun clearPref() {
        sharedPrefs.edit().clear().apply()
    }

    private fun saveHistory(): MutableList<TrackDto> {
        var listSaveHistory = mutableListOf<TrackDto>()
        val loadGson = sharedPrefs.getString(KEY, "")
        if (loadGson != "") {
            listSaveHistory = json.fromJson(loadGson, object : TypeToken<List<TrackDto>>() {}.type)
        }
        return listSaveHistory
    }

    companion object {
        const val SAVE_TRACKS = "saveTracks"
        private const val KEY = "key"
    }
}