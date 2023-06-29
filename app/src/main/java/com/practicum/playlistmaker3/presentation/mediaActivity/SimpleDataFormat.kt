package com.practicum.playlistmaker3.presentation.mediaActivity

import java.text.SimpleDateFormat
import java.util.*

interface SimpleDataFormat {
    fun simpleDateFormat(time: String): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(time.toLong())
    }
}
