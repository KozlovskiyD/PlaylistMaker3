package com.practicum.playlistmaker3.mediaLibrary.data.db.dao

import androidx.room.*
import com.practicum.playlistmaker3.mediaLibrary.data.db.entity.PlaylistEntity

@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Update
    suspend fun updatePlaylist(playlist: PlaylistEntity)

    @Delete
    suspend fun deletePlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlist_table")
    suspend fun getPlaylist(): List<PlaylistEntity>

    @Query("SELECT * FROM playlist_table WHERE id = :Id")
    suspend fun getCurrentPlaylist(Id: Int): PlaylistEntity
}