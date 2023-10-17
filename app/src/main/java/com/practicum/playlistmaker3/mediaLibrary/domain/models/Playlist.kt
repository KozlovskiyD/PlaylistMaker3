package com.practicum.playlistmaker3.mediaLibrary.domain.models

data class Playlist(
    val id: Int? = null,
    var namePlaylist: String? = null,
    var description: String? = null,
    var filePath: String? = null,
    val trackList: MutableList<Long> = mutableListOf(),
    var trackCount: Int = 0,
) : java.io.Serializable
