package com.practicum.playlistmaker3.domain.api

import java.text.SimpleDateFormat
import java.util.*

interface SimpleDataFormat {
    fun simpleDateFormat(time: String): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(time.toLong())
    }
}
