package com.practicum.playlistmaker3.util

fun getTrackNumber(number: Int): String {
    val countTrack = number % 100
    if (countTrack in 11..14)
        return "треков"
    return when (countTrack % 10) {
        1 -> "трек"
        2, 3, 4 -> "трека"
        else -> "треков"
    }
}

fun getTrackMinutes(number: Int): String {
    val countTrack = number % 100
    if (countTrack in 11..14)
        return "минут"
    return when (countTrack % 10) {
        1 -> "минута"
        2, 3, 4 -> "минуты"
        else -> "минут"
    }
}