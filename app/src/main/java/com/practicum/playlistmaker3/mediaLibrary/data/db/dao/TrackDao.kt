package com.practicum.playlistmaker3.mediaLibrary.data.db.dao

import androidx.room.*
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.TrackEntity
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.TrackId

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Delete
    suspend fun deleteTrack(track: TrackEntity)

    @Query("SELECT * FROM track_table")
    suspend fun getTracks(): List<TrackEntity>

    @Query("SELECT trackId FROM track_table")
    suspend fun getListId(): List<TrackId>
}