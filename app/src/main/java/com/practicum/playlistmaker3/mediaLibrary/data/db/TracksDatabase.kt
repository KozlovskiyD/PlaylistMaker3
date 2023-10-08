package com.practicum.playlistmaker3.mediaLibrary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmaker3.mediaLibrary.data.db.dao.TrackDao
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.TrackEntity

@Database(version = 1, entities = [TrackEntity::class])
abstract class TracksDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao
}