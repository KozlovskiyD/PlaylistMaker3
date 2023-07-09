package com.practicum.playlistmaker3.search.domain.models

data class Track(
    val trackId: String,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: String,
    val artworkUrl100: String,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String
) : java.io.Serializable

fun getCoverArtwork(art: String): String {
    return art.replaceAfterLast('/', "512x512bb.jpg")
}