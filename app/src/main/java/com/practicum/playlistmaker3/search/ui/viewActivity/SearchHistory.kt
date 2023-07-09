package com.practicum.playlistmaker3.search.ui.viewActivity


import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.search.ui.viewActivity.SearchActivity.Companion.KEY


class SearchHistory(private val sharedPrefs: SharedPreferences) {

    private var listSave = mutableListOf<Track>()

    fun saveHistory(saveTrack: Track, current: Boolean) {
        if (current) {
            val loadGson = sharedPrefs.getString(KEY, "")
            if (loadGson != "") listSave = Gson().fromJson(loadGson, object : TypeToken<ArrayList<Track>>() {}.type)
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
    }
}