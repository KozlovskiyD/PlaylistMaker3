package com.practicum.playlistmaker3.mediaLibrary.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int? = 0,
    val name: String?,
    val description: String?,
    val filePath: String?,
    var trackList: String?,
    val trackCount: Int = 0,
)