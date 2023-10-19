package com.practicum.playlistmaker3.mediaLibrary.data.db.dao

import androidx.room.*
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.PlaylistTrackEntity

@Dao
interface PlaylistTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: PlaylistTrackEntity)

    @Delete
    suspend fun deleteTrack(track: PlaylistTrackEntity)

    @Query("SELECT * FROM playlist_track_table WHERE trackId = :id")
    suspend fun getCurrentListTrack(id: String): PlaylistTrackEntity?
}