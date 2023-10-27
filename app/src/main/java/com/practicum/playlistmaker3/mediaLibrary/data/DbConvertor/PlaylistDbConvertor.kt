package com.practicum.playlistmaker3.mediaLibrary.data.DbConvertor

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.PlaylistEntity
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist

@Suppress("UNUSED_EXPRESSION")
class PlaylistDbConvertor(private var json: Gson) {

    fun map(playlist: Playlist): PlaylistEntity {
        return PlaylistEntity(
            id = playlist.id,
            name = playlist.namePlaylist,
            description = playlist.description,
            filePath = playlist.filePath,
            trackList = json.toJson(playlist.trackList).toString(),
            trackCount = playlist.trackCount
        )
    }

    fun map(playlist: PlaylistEntity): Playlist {
        return Playlist(
            id = playlist.id!!,
            namePlaylist = playlist.name,
            description = playlist.description,
            filePath = playlist.filePath,
            trackList = createTrackListFromJson(playlist.trackList),
            trackCount = playlist.trackCount
        )
    }

    private fun createTrackListFromJson(jsonTrackList: String?): MutableList<Long> {
        var trackIdList = mutableListOf<Long>()
        if (!jsonTrackList.isNullOrEmpty()) {
            trackIdList = json.fromJson(jsonTrackList, object : TypeToken<List<Long>>() {}.type)
        }
        return trackIdList
    }
}