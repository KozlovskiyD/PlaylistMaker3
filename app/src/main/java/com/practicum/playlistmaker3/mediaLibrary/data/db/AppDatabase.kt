package com.practicum.playlistmaker3.mediaLibrary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmaker3.mediaLibrary.data.db.dao.PlaylistDao
import com.practicum.playlistmaker3.mediaLibrary.data.db.dao.PlaylistTrackDao
import com.practicum.playlistmaker3.mediaLibrary.data.db.dao.TrackDao
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.PlaylistEntity
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.PlaylistTrackEntity
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.TrackEntity

@Database(
    version = 1,
    entities = [TrackEntity::class, PlaylistEntity::class, PlaylistTrackEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun playlistTrackDao(): PlaylistTrackDao
}