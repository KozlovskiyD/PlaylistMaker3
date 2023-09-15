package com.practicum.playlistmaker3.mediaLibrary.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
data class TrackEntity(
    @PrimaryKey @ColumnInfo(name = "trackId")
    val trackId: String,
    @ColumnInfo(name = "trackName")
    val trackName: String,
    @ColumnInfo(name = "artistName")
    val artistName: String,
    @ColumnInfo(name = "trackTimeMillis")
    val trackTimeMillis: String,
    @ColumnInfo(name = "artworkUrl100")
    val artworkUrl100: String,
    @ColumnInfo(name = "collectionName")
    val collectionName: String,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,
    @ColumnInfo(name = "primaryGenreName")
    val primaryGenreName: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "previewUrl")
    val previewUrl: String
)
