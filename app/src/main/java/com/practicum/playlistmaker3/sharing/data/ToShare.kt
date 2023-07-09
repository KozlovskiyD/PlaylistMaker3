package com.practicum.playlistmaker3.sharing.data

import android.content.Context
import android.content.Intent
import com.practicum.playlistmaker3.R

class ToShare {
    fun shareTheApp(context: Context) {
        Intent(Intent.ACTION_SEND).apply {
            val message = context.getString(R.string.uriDeveloper)
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
            context.startActivity(this)
        }
    }
}