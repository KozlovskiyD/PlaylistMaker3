package com.practicum.playlistmaker3.presentation.mediaActivity

interface GetCoverArtwork {

    fun getCoverArtwork(art: String): String {
        return art.replaceAfterLast('/', "512x512bb.jpg")
    }
}