package com.practicum.playlistmaker3.mediaLibrary.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "filePath")
    val filePath: String?,
    @ColumnInfo(name = "trackList")
    var trackList: String?,
    @ColumnInfo(name = "trackCount")
    val trackCount: Int = 0,
)