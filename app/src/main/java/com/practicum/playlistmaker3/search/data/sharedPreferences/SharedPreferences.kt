package com.practicum.playlistmaker3.search.data.sharedPreferences

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker3.search.data.SharedPrefClient
import com.practicum.playlistmaker3.search.data.dto.TrackDto

class SharedPreferences(context: Context) : SharedPrefClient {

    private var sharedPrefs = context.getSharedPreferences(SAVE_TRACKS, Context.MODE_PRIVATE)

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
        val json = Gson().toJson(listSave)
        sharedPrefs.edit()
            .putString(KEY, json.toString())
            .apply()
    }

    override fun clearPref() {
        sharedPrefs.edit().clear().apply()
    }

    private fun saveHistory(): MutableList<TrackDto> {
        var listSave = mutableListOf<TrackDto>()
        val loadGson = sharedPrefs.getString(KEY, "")
        if (loadGson != "") {
            listSave = Gson().fromJson(loadGson, object : TypeToken<List<TrackDto>>() {}.type)
        }
        return listSave
    }

    companion object {
        const val SAVE_TRACKS = "saveTracks"
        const val KEY = "key"
    }
}