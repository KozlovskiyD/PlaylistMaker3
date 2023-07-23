package com.practicum.playlistmaker3.sharing.data

import android.content.Context
import android.content.Intent
import com.practicum.playlistmaker3.R

class ToShare(private val context: Context) {
    fun shareTheApp() {
        Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val message = context.getString(R.string.uriDeveloper)
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
            context.startActivity(this)
        }
    }
}